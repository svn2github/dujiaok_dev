#!/bin/bash
####################################################################################
# !!!DO NOT EDIT!!!
# This script is designed to be invoked both by RPM and manually, so that
# it can be used by QA and OPS, and handle single-machine installation, multi-machine
# installation or maual re-configuration and re-installation etc situations.
# See readme.txt for more.
#-----------------------------------------------------------------------------------

function fail () {
  echo -e "\e[00;31m$1\e[00m"
}

function auto_config () {
  local LOG=$TARGET/rpm/antxconfig.log
  local SUCCESSMSG1='总耗费时间'
  local SUCCESSMSG2='Generating log file'
  local RUNNING=-1
  local SUCCESS=-1
  echo "Executing antxconfig -u $ANTX_PROP $*"
  
  rm -f $LOG
  if [ $NONINTERACT ]; then
    antxconfig -I -u $ANTX_PROP $* > $LOG
  else
    antxconfig -u $ANTX_PROP $* | tee $LOG
  fi
  
  for i in {2..0}; do
    SUCCESS1=`tail -n 3 $LOG | grep -c "$SUCCESSMSG1"`
    SUCCESS2=`tail -n 5 $LOG | grep -c "$SUCCESSMSG2"`
    if [[ $SUCCESS1 == 1 && $SUCCESS2 == 1 ]]; then
      return
    fi
    echo -e ".\c"
    sleep $i
  done
  
  fail "Error: antxconfig failed, abort installation, check $LOG"
  exit 30
}

function deploy () {
	echo "Start deploy $1 to $2..."
	
  local SRC=$1
  local DEST=$2
  check_deploy $*
  
  if [ -f $SRC ]; then
    local PKG=$TARGET/tmp/`basename $SRC`
    unpackage $SRC $PKG
    SRC=$PKG
  fi
  
  for x in $SRC/*; do
    rsync -avzq --delete $x $DEST &>/dev/null
    if [ $? != 0 ]; then
      fail "Error: rsync $x $DEST failed, abort installation."
      exit 60
    fi
  done
	echo "done"
}

function exit_root () {
  if [ `id -u` = 0 ]; then
    fail "Error: root (the superuser) can't do the installation."
    exit 10
  fi
}

function check_antxprop () {
  ANTX_PROP=`dirname $TARGET`/antx.properties
  if [ ! -f $ANTX_PROP ]; then
    fail "WARN: antx.properties is missing, abort installation."
    echo -e "\nYou can manually (re)install as follows:"
    echo "1. Put antx.properties file in `dirname $0` or home directory"
    echo "2. Execute $TARGET/rpm/install.sh"
    exit 20
  fi
  echo "Using $ANTX_PROP for auto-config..."
}

function check_deploy () {
  local SRC=$1
  local DEST=$2
  
  if [ ! -e $SRC ]; then
    fail "BUG: deploy source $SRC does not exist, abort installation."
    exit 100
  fi
  if [ -e $DEST ]; then
    if [ ! -d $DEST ]; then
      ls -l $DEST
      fail "Error: deploy dest $DEST is not a folder, abort installation."
      exit 50
    fi
  else
    mkdir -p $DEST
  fi
}

function unpackage () {
  local SRC=$1
  local PKG=$2
  rm -rf $PKG
  mkdir -p $PKG
  local suffix=`echo ${SRC##*.}`
  if [ $suffix = 'jar' ]; then
    unzip -qq $SRC -d $PKG
  elif [ $suffix = 'war' ]; then
    unzip -qq $SRC -d $PKG
  elif [ $suffix = 'tgz' ]; then
    tar xzf $SRC -C $PKG
  else
    fail "BUG: deploy source is not a [jar,war,tgz]: $SRC"
    exit 100
  fi
  if [ $? != 0 ] ; then
    fail "Error: unpackage $SRC failed."
    exit 40
  fi
}

echo "Usage: install.sh [--non-interactive] [--config] [--deploy-war] [--deploy-template] [--deploy-task]"
exit_root

cd `dirname $0`
TARGET=`pwd`
TARGET=`dirname $TARGET`
echo "TARGET = $TARGET"

#collect a few built-in arguments
TEMP=`getopt -l "non-interactive,byrpm,config,deploy-war,deploy-task,deploy-template" -- "" "$@"`
if [ $? != 0 ]; then
  fail "Invalid install options, abort installation."
  exit 1
fi
for i in ${TEMP[@]}; do
  case "$i" in
  "--non-interactive")  NONINTERACT=true;;
  "--byrpm")            BYRPM=true;;
  "--config")           CONFIG=true;;
  "--deploy-war")       WAR=true;;
  "--deploy-task")      TASK=true;;
  "--deploy-template")  TEMPLATE=true;;
  esac
done

#if no arguments are given, do it all
if [[ ! $CONFIG && ! $WAR && ! $TASK && ! $TEMPLATE ]]; then
  CONFIG=true; WAR=true; TASK=true; TEMPLATE=true;
fi

#do auto-config (install from rpm should ALWAYS do it)
ANTXCONFIG_OK=$TARGET/rpm/antxconfig.ok
if [[ $CONFIG || $BYRPM ]]; then
  rm -f $ANTXCONFIG_OK
  check_antxprop
  chmod 755 $TARGET/rpm/app-config.sh
  . $TARGET/rpm/app-config.sh
  auto_config -d auto-config.xml $TARGET/rpm
  touch $ANTXCONFIG_OK
fi

#don't do app-deploy if antxconfig has not done yet
if [ ! -f $ANTXCONFIG_OK ]; then
  fail "Auto-config has not done yet, abort installation"
  exit 1
fi

#env.sh is required for app-deploy
chmod 755 $TARGET/rpm/env.sh
. $TARGET/rpm/env.sh

#callback app-deploy.sh
chmod 755 $TARGET/rpm/app-deploy.sh
. $TARGET/rpm/app-deploy.sh
if [ $WAR ];      then declare -F deploy_war && deploy_war; fi
if [ $TEMPLATE ]; then declare -F deploy_template && deploy_template; fi
if [ $TASK ];     then declare -F deploy_task && deploy_task; fi
exit 0