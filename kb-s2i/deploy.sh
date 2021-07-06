#!/usr/bin/env bash

cp -- /tmp/src/yousee-channel-archiving-requester-web/conf/ocp/logback.xml "$CONF_DIR/."
 
ln -s -- "$TOMCAT_APPS/channel-archiving-requester.xml" "$DEPLOYMENT_DESC_DIR/channel-archiving-requester.xml"
