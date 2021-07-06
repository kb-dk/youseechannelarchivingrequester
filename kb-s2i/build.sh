#!/usr/bin/env bash

cd /tmp/src

cp -rp -- /tmp/src/yousee-channel-archiving-requester-web/target/yousee-channel-archiving-requester-*.war "$TOMCAT_APPS/channel-archiving-requester.war"
cp -- /tmp/src/yousee-channel-archiving-requester-web/conf/ocp/channel-archiving-requester.xml "$TOMCAT_APPS/channel-archiving-requester.xml"

export WAR_FILE=$(readlink -f "$TOMCAT_APPS/channel-archiving-requester.war")
