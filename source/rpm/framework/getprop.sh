#/bin/bash

BASE=`pwd`
CHECKWORD='saved'
OUTPUT=$HOME/antx.properties
OUTPUT_LOG="$HOME/.figo_log"
rm $OUTPUT_LOG -f
PASSWD_FILE="$HOME/.figo"
CONTENT=""
ENV="test"

function fail () {
  echo -e "\e[00;31m$1\e[00m"
}

if [[ $# == 0 || $1 == "--help" ]]; then
  echo 'Usage: getprop.sh -a APP [-e ENV]'
  echo 'ENV = dev, test(default), online'
  exit 1;
fi

while getopts a:e: ARG
do
  case $ARG in
    a) APP=$OPTARG;;
    e) ENV=$OPTARG ;;
  esac
done

if [[ $ENV = "hz.online" || $ENV = "qd.online" ]]; then
  ENV="$ENV`cat $PASSWD_FILE`"
fi

wget "http://10.20.132.21:1999/antxconfig/figo/figo.htm?project=$APP&group=$ENV" -O $OUTPUT -o $OUTPUT_LOG

WORD=`tail -2 $OUTPUT_LOG | head -1 | awk '{print $6}'`
CONTENT=`cat $OUTPUT | head`

if [[ ! $WORD = $CHECKWORD || -z $CONTENT ]]; then
  fail "Download Properties Failed!"
  exit 1
else
  echo "Download Properties Done!"
  exit 0
fi
