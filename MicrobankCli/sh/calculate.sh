#!/bin/sh
# Aplikasi untuk mendebet secara otomatis
# Copyright by Iwan A Fatahi (iwanfatahi@gmail.com)
# Dicontoh dari jboss

DIRNAME=`dirname "$0"`
PROGNAME=`basename "$0"`
APP_JAR=CliRevenue.jar
APP_CLASS=org.simbiosis.cli.revsharing.Calculate
GREP="grep"

# Use the maximum available, or set MAX_FD != -1 to use that
MAX_FD="maximum"

# Setup APP_HOME
RESOLVED_APP_HOME=`cd "$DIRNAME"; pwd`
if [ "x$APP_HOME" = "x" ]; then
    # get the full path (without any relative bits)
    APP_HOME=$RESOLVED_APP_HOME
else
 SANITIZED_APP_HOME=`cd "$APP_HOME"; pwd`
 if [ "$RESOLVED_APP_HOME" != "$SANITIZED_APP_HOME" ]; then
   echo "WARNING APP_HOME may be pointing to a different installation - unpredictable results may occur."
   echo ""
 fi
fi
export APP_HOME

# Setup the JVM
if [ "x$JAVA" = "x" ]; then
    if [ "x$JAVA_HOME" != "x" ]; then
        JAVA="$JAVA_HOME/bin/java"
    else
        JAVA="java"
    fi
fi

COMMON="$APP_HOME/lib/commons-logging-1.1.1.jar:$APP_HOME/lib/httpclient-4.2.jar:$APP_HOME/lib/httpcore-4.2.jar"
JACKSON="$APP_HOME/lib/jackson-core-asl-1.6.9.jar:$APP_HOME/lib/jackson-mapper-asl-1.6.9.jar"
JODATIME="$APP_HOME/lib/joda-time-1.6.2-redhat-4.jar"
SIMBIOSIS="$APP_HOME/simbiosis/SystemDto.jar:$APP_HOME/simbiosis/MicrobankDto.jar:$APP_HOME/simbiosis/CliSimbiosis.jar"
CLASSPATH="$APP_HOME/$APP_JAR:$COMMON:$JACKSON:$JODATIME:$SIMBIOSIS"

# Execute the JVM in the foreground
eval \"$JAVA\" $JAVA_OPTS -cp "$CLASSPATH" $APP_CLASS
APP_STATUS=$?
exit $APP_STATUS
