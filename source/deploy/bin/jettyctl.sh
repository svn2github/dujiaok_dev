#!/usr/bin/env bash 

#fileName jettyctl.sh

function usage(){
	echo "Usage : ${0##*/} [start | debug |stop | restart | h | help | ckeck] [ jmx ]"
	exit;
}

LegalArguments=("start" "debug" "stop" "restart" "h" "help" "jmx")
arguments=$@
for arg in ${arguments[@]} ; do
	legal=0	
	if [ $arg == 'h' ] || [ $arg == 'help' ] ; then
		usage
	fi
	for legalArg in ${LegalArguments[@]} ;	do
		if [ $arg == $legalArg ] ; then
			legal=1;
			break
		fi
	done	
	if [ $legal == 0 ] ; then
		usage
	fi
done



cd `dirname $0`/..
BASE=`pwd`

. "$BASE/bin/env.sh"

if [ ! -d "$JETTY_SERVER_HOME/etc" ] 
then
	mkdir "$JETTY_SERVER_HOME/etc"	
fi 

cp  "$DOUCOME_DEPLOY_HOME/conf/jetty/etc/jetty.xml" "$JETTY_SERVER_HOME/etc/jetty.xml"
cp  "$DOUCOME_DEPLOY_HOME/conf/jetty/etc/jetty-logging.xml" "$JETTY_SERVER_HOME/etc/jetty-logging.xml"
cp  "$DOUCOME_DEPLOY_HOME/conf/jetty/etc/jetty-jmx.xml" "$JETTY_SERVER_HOME/etc/jetty-jmx.xml"
cp  "$DOUCOME_DEPLOY_HOME/conf/jetty/etc/jetty-deploy.xml" "$JETTY_SERVER_HOME/etc/jetty-deploy.xml"
cp  "$DOUCOME_DEPLOY_HOME/conf/jetty/etc/jetty-webapps.xml" "$JETTY_SERVER_HOME/etc/jetty-webapps.xml"
cp  "$DOUCOME_DEPLOY_HOME/conf/jetty/etc/webdefault.xml" "$JETTY_SERVER_HOME/etc/webdefault.xml"
cp  "$DOUCOME_DEPLOY_HOME/conf/jetty/start.ini" "$JETTY_SERVER_HOME/start.ini"


webapps="$JETTY_SERVER_HOME/webapps"

if [ ! -d "$webapps" ] 
then
	mkdir "$webapps"	
fi 

if  $production_mode ; then
	echo "start copy war $DOUCOME_DEPLOY_HOME/web.war to $JETTY_SERVER_HOME/webapps/root.war"
	rm -rf  "$JETTY_SERVER_HOME/webapps/root.war" 
	cp "$DOUCOME_DEPLOY_HOME/web.war"  "$JETTY_SERVER_HOME/webapps/root.war" 
else
	cp  "$DOUCOME_DEPLOY_HOME/../dujiaok.bundle.war-1.0-SNAPSHOT.war"  "$JETTY_SERVER_HOME/webapps/root.war"
fi




JAVA_OPTS="$JAVA_OPTS $JAVA_DEBUG_OPT $TIGER_JMX_OPT "
export JAVA_OPTS 

JETTY_BASE="$JETTY_SERVER_HOME"
LOGDIR="$OUTPUT_HOME/logs"

if [ ! -d "$LOGDIR" ] ; then
	mkdirhier "$LOGDIR"
fi

TMPDIR="$JETTY_BASE/tmp"

if [ ! -d "$TMPDIR" ] ; then
	mkdirhier "$TMPDIR"
fi

JETTY_START=$JETTY_HOME/start.jar
[ ! -f "$JETTY_START" ] && JETTY_START=$JETTY_HOME/lib/start.jar

JAVA_EXE="$JAVA_HOME/bin/java"

if [ ! -f  $JAVA_EXE ] && [ ! -x $JAVA_EXE ] ; then
	echo "No executable java was found on the path : $JAVA_EXE"
	exit;
fi

SUN_JAVA_TOOLS="$JAVA_HOME/lib/tools.jar"
if [ ! -f  $SUN_JAVA_TOOLS ] ; then
	echo "No java class packets was found on the path : $SUN_JAVA_TOOLS"
	exit;
fi

JETTY_INI="$JETTY_BASE/start.ini"

RUN_ARGS=(${JAVA_OPTS[@]} -jar $JETTY_START --ini=$JETTY_INI lib=$JAVA_HOME/lib)
RUN_CMD=("$JAVA_EXE" ${RUN_ARGS[@]})

JETTY_PID="$BASE/bin/jetty.pid"


have_tty=0
if [ "`tty`" != "not a tty" ]; then
    have_tty=1
fi

echo "JETTY_HOME     =  $JETTY_HOME"
echo "JETTY_PID      =  $JETTY_PID"
echo "JETTY_INI      =  $JETTY_INI"
echo "JAVA_OPTIONS   =  ${JAVA_OPTS[*]}"
echo "JAVA           =  $JAVA_EXE"
echo "RUN_CMD        =  ${RUN_CMD[*]}"

shift
eval ${RUN_CMD[*]} "&"

if [ ! -z "$JETTY_PID" ]; then
	echo $! > "$JETTY_PID"
fi