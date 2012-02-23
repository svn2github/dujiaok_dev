#!/bin/bash

echo "TARGET=$TARGET , WEBAPP_HOME=$WEBAPP_HOME"

function deploy_template() {
  deploy  $TARGET/web-deploy.jar $WEBAPP_HOME
  chmod 755 $WEBAPP_HOME/bin/*
}

function deploy_task() {
  ## deploy_template
  ## mkdir -p $TASK_HOME 
  ## cp -r $TARGET/mobiletrade.task.*-1.0-SNAPSHOT.jar $TASK_HOME 
}

function deploy_war() {
  if [ $PRODUCTION == true ]; then
    mkdir -p $OUTPUT_HOME
    cp  $TARGET/dujiaok.webapp.war-1.0-SNAPSHOT.war $WEBAPP_HOME/web.war
  else
    mkdir -p $WEBAPP_HOME
    cp  $TARGET/dujiaok.webapp.war-1.0-SNAPSHOT.war $WEBAPP_HOME/..
  fi
}
