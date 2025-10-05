#!/bin/sh
JAVA_OPT="${JAVA_OPT:-"-XX:+UseNUMA -XX:+UseG1GC -Xms1G -Xmx1G"}"
java $JAVA_OPTS -jar /app.jar
