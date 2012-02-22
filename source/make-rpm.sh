#!/bin/bash

cd `dirname $0`
BASE=`pwd`
mvn clean install -Dmaven.test.skip=true -Denv=release -Dautoconf.skip=true -B
ant -DappName=mobiletrade -DversionFormat=yyyyMMdd -Dbasedir=$BASE -f $BASE/bundle/rpm/framework/build.xml
