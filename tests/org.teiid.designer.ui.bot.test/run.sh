#!/bin/sh
DISPLAY=:2 mvn clean integration-test -P!unified.target,!jbosstools-staging-aggregate,jbds5