@echo off

set APP_JAR=CliFinancialReport.jar
set APP_CLASS=org.simbiosis.cli.financial.CreatorBulk

set APP_HOME="/home/application"
set COMMON="%APP_HOME%/lib/commons-logging-1.1.1.jar;%APP_HOME%/lib/httpclient-4.2.jar;%APP_HOME%/lib/httpcore-4.2.jar"
set JACKSON="%APP_HOME%/lib/jackson-core-asl-1.6.9.jar;%APP_HOME%/lib/jackson-mapper-asl-1.6.9.jar"
set SIMBIOSIS="%APP_HOME%/simbiosis/SystemDto.jar:%APP_HOME%/simbiosis/MicrobankDto.jar;%APP_HOME%/simbiosis/GlDto.jar;%APP_HOME%/simbiosis/Utils.jar;%APP_HOME%/simbiosis/CliSimbiosis.jar"
set CLASSPATH="%APP_HOME%/%APP_JAR%;%COMMON%;%JACKSON%;%SIMBIOSIS%"

rem Execute the JVM in the foreground
"%JAVA%" -cp "%CLASSPATH%" %APP_CLASS%

