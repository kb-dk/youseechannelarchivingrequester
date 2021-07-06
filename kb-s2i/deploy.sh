#!/usr/bin/env bash

cp -- /tmp/src/conf/* "$CONF_DIR/"
 
ln -s -- "$TOMCAT_APPS/channel-archiving-requester.xml" "$DEPLOYMENT_DESC_DIR/channel-archiving-requester.xml"
