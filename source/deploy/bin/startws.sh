#!/bin/bash

function exit_root () {
    echo
    echo "ERROR! root (the superuser) can't run this script."
    echo
    exit 1
}

if [ `id -u` = 0 ]
then
    exit_root
fi


export LANG=zh_CN.GB18030
cd `dirname $0`
BASE_BIN_DIR=`pwd`

# public functions      
. $BASE_BIN_DIR/functions.sh


#import home var env
. $BASE_BIN_DIR/env.sh

HOST_NAME=`hostname`
LOG_DIR=$OUTPUT_HOME/logs
JETTY_PID="$OUTPUT_HOME/logs/jetty.pid"
##CHECK LOG
WLS_CHECK_LOG="$LOG_DIR/admin_server_out.log"
JETTY_CHECK_LOG="$LOG_DIR/jetty_stdout.log"
APACHE_LOG="$LOG_DIR/apache_error.log"
##save old LOGs
LOGS_SAVED="$LOG_DIR/logs_saved"
##Success FLAG
SUCCESSMSG="Started SelectChannelConnector@"

TIMESTAMP=`date +%Y_%m_%d_%H_%M`

##hummock create log directory 
HUMMOCK_LOGDIR="$LOG_DIR/hummock"
if [ ! -d $HUMMOCK_LOGDIR ]; then
   mkdir -p $HUMMOCK_LOGDIR
fi


#backup logs
if [ ! -d $LOGS_SAVED ]; then
   mkdir -p $LOGS_SAVED
fi

if [ -f $JETTY_CHECK_LOG ]; then
   mv -f $JETTY_CHECK_LOG $LOGS_SAVED/jetty_stdout.log.$TIMESTAMP
fi
if [ -f $APACHE_LOG ]; then
   mv -f $APACHE_LOG $LOGS_SAVED/apache_error.log.$TIMESTAMP
fi
##copy server home
if [ ! -d "$JETTY_SERVER_HOME" ]; then
    rm -f $JETTY_SERVER_HOME
    mkdir -p $JETTY_SERVER_HOME
    ##cp -rf $JBOSS_HOME/server/default/. $JBOSS_SERVER_HOME/.
else
    rm -rf $JETTY_SERVER_HOME/tmp
    ##rm -rf $JETTY_SERVER_HOME/work
    ##rm -rf $JETTY_SERVER_HOME/log
fi
##check if started before
STR=''
if $cygwin; then
    JAVA_CMD="$JAVA_HOME\bin\java"
    JAVA_CMD=`cygpath --path --unix $JAVA_CMD`
    STR=`ps | grep "$JAVA_CMD"`  
else
    STR=`ps -C java -f --width 1000 | grep "$JETTY_SERVER_DIR"`
        
fi
if [ ! -z "$STR" ]; then
    echo "jetty server already started"
    exit;
fi    
    
## start Jetty 
echo -e "$HOST_NAME: starting jetty ...\c"
$BASE_BIN_DIR/jettyctl.sh start 1>$JETTY_CHECK_LOG 2>$JETTY_CHECK_LOG &
COUNT=0
while test $COUNT -lt 1
do
    COUNT=`grep -c "$SUCCESSMSG" $JETTY_CHECK_LOG`
    echo -e ".\c"
    sleep 3
done

sleep 20

success "Oook!"

##Start apache
if ! $cygwin; then
    echo -e "$HOST_NAME: starting httpd ..."
    chmod +x $BASE_BIN_DIR/httpd
    $BASE_BIN_DIR/httpd restart > $APACHE_LOG 2>&1 &
    success "Oook!"
    echo -e "$HOSTNAME: reloadws_alone done!"
else
    echo -e "$HOSTNAME: Cygwin mode, skip start apache!"
fi    
