#!/bin/bash

echo "run app-config.sh target = $TARGET"

auto_config $TARGET/dujiaok.webapp-1.0-SNAPSHOT.war
auto_config $TARGET/web-deploy.jar
