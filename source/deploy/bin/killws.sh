#!/bin/bash
# stop httpd server and application server
cd `dirname $0`

BASE_BIN_DIR=`pwd`

# public functions      
. $BASE_BIN_DIR/functions.sh

#import home var env
. $BASE_BIN_DIR/env.sh

DOMAINS=$HOME/domains
APACHECTL=$BASE_BIN_DIR/httpd
APACHE_PID_FILE=$OUTPUT_HOME/logs/httpd.pid
TIMESTAMP=`date +%Y_%m_%d_%H:%M`
JETTY_PID="$OUTPUT_HOME/logs/jetty.pid"

HOST_NAME=`hostname`

stop_jetty()
{
	SLEEP=5		 

	if [ ! -z "$JETTY_PID" ]; then
		if [ -s "$JETTY_PID" ]; then
		  	if [ -f "$JETTY_PID" ]; then
				kill -0 `cat "$JETTY_PID"` >/dev/null 2>&1
				if [ $? -gt 0 ]; then
			  		echo "PID file found but no matching process was found. Stop aborted."
			  		exit 1
				fi
		  	else
				echo "\$JETTY_PID was set but the specified file does not exist. Is Jetty running? Stop aborted."
				exit 1
		  	fi
		else
		  	echo "PID file is empty and has been ignored."
		fi
	fi
	if [ ! -z "$JETTY_PID" ]; then
		if [ -f "$JETTY_PID" ]; then
		  	while [ $SLEEP -ge 0 ]; do
			kill -0 `cat "$JETTY_PID"` >/dev/null 2>&1
			if [ $? -gt 0 ]; then
		  		rm -f "$JETTY_PID" >/dev/null 2>&1
		  		if [ $? != 0 ]; then
					if [ -w "$JETTY_PID" ]; then
			  			cat /dev/null > "$JETTY_PID"
					else
			  			echo "Jetty stopped but the PID file could not be removed or cleared."
					fi
		  		fi
		  		break
			fi
			if [ $SLEEP -gt 0 ]; then
		  		sleep 1
			fi			
			SLEEP=`expr $SLEEP - 1 `
	  		done
		fi
	fi

	if [ -z "$JETTY_PID" ]; then
		echo "Kill failed: \$JETTY_PID not set"
	else
		if [ -f "$JETTY_PID" ]; then
			PID=`cat "$JETTY_PID"`
			echo "Killing Jetty with the PID: $PID"
			kill -9 $PID
			rm -f "$JETTY_PID" >/dev/null 2>&1
			if [ $? != 0 ]; then
			  	echo "Jetty was killed but the PID file could not be removed."
			fi
		fi
	fi
}

get_pid()
{
    if $cygwin; then
        JAVA_CMD="$JAVA_HOME\bin\java"
        JAVA_CMD=`cygpath --path --unix $JAVA_CMD`        
        JBOSS_JAVA_PID=`ps |grep $JAVA_CMD |awk '{print $1}'`                
    else
        STR=`ps -C java -f --width 1000 | grep "$JBOSS_SERVER_DIR"`
        if [ ! -z "$STR" ]; then                 
            JBOSS_JAVA_PID=`ps --width 1000 auxef|grep "$JBOSS_SERVER_DIR"|grep org.jboss.Main |grep -v grep|awk '{print $2}'`
        fi           
    fi
    echo $JBOSS_JAVA_PID;
}

# stop httpd server
if [ -f $APACHE_PID_FILE ] ; then
    echo -e "$HOST_NAME: stopping httpd ...\c"
    $APACHECTL stop > /dev/null 2>&1
    sleep 5
    killall -9 httpd
    killall -9 hummockclient
    ipcs -m|grep admin|awk '{print $2}'|xargs ipcrm shm
    ipcs -s|grep admin|awk '{print $2}'|xargs ipcrm sem
    success "Oook!"
else
    warning "$HOST_NAME: httpd not running, who care?"
fi

# stop jboss server 
if (ps uxc| grep java | grep -v grep >/dev/null 2>&1) ; then
    echo -e "$HOST_NAME: stopping jetty ... "
    stop_jetty;
    success "Oook!"
else
   warning "$HOST_NAME: jetty not running, who care?"
fi
