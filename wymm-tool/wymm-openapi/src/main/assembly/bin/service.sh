#!/bin/bash

source "`dirname "$0"`"/service-env.sh

cd $(dirname $0)/..
base_dir=`pwd`

PIDS=""
function findPid() {
  PIDS=$(ps ax | grep -i "${base_dir}/${JAR_NAME}" | grep java | grep -v grep | awk '{print $1}')
}

if [ "$1" = "start" ] ; then
  findPid
  if [ "x${PIDS}" != 'x' ]; then
    echo ${JAR_NAME} has been running!!!!!
    exit 1
  fi

  while [ $# -gt 0 ]; do
    COMMAND=$1
    case $COMMAND in
      -name)
        DAEMON_NAME=$2
        shift 2
        ;;
      -loggc)
        if [ -z "$SERVICE_GC_LOG_OPTS" ]; then
          GC_LOG_ENABLED="true"
        fi
        shift
        ;;
      -daemon)
        DAEMON_MODE="true"
        shift
        ;;
      *)
        shift
        ;;
    esac
  done

  # Which java to use
  if [ -z "$_JAVA_HOME" ]; then
    JAVA="java"
  else
    JAVA="$_JAVA_HOME/bin/java"
  fi

  # Memory options
  if [ -z "$SERVICE_HEAP_OPTS" ]; then
    SERVICE_HEAP_OPTS="-Xmx256M"
  fi

  # JVM performance options
  # MaxInlineLevel=15 is the default since JDK 14 and can be removed once older JDKs are no longer supported
  if [ -z "$SERVICE_JVM_PERFORMANCE_OPTS" ]; then
    SERVICE_JVM_PERFORMANCE_OPTS="-server -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -Djava.awt.headless=true"
  fi

  # GC options
  GC_FILE_SUFFIX='-gc.log'
  GC_LOG_FILE_NAME=''
  if [ "x$GC_LOG_ENABLED" = "xtrue" ]; then
    GC_LOG_FILE_NAME=$DAEMON_NAME$GC_FILE_SUFFIX
    # 8 -> java version "1.8.0_152"
    SERVICE_GC_LOG_OPTS="-Xloggc:logs/$GC_LOG_FILE_NAME -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
  fi

  # Set Debug options if enabled
  if [ "x$SERVICE_DEBUG" != "x" ]; then

      # Use default ports
      DEFAULT_JAVA_DEBUG_PORT="5005"

      if [ -z "$JAVA_DEBUG_PORT" ]; then
          JAVA_DEBUG_PORT="$DEFAULT_JAVA_DEBUG_PORT"
      fi

      # Use the defaults if JAVA_DEBUG_OPTS was not set
      DEFAULT_JAVA_DEBUG_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$JAVA_DEBUG_PORT"
      if [ -z "$JAVA_DEBUG_OPTS" ]; then
          JAVA_DEBUG_OPTS="$DEFAULT_JAVA_DEBUG_OPTS"
      fi

      echo "Enabling Java debug options: $JAVA_DEBUG_OPTS"
      SERVICE_OPTS="$JAVA_DEBUG_OPTS $SERVICE_OPTS"
  fi


  # Spring options
  if [ -z "$SPRING_CONFIG_OPTS" ]; then
    SPRING_CONFIG_OPTS=""
  fi

  # Launch mode
    COMMAND="${JAVA} ${SERVICE_HEAP_OPTS} ${SERVICE_JVM_PERFORMANCE_OPTS} ${SERVICE_GC_LOG_OPTS} ${SERVICE_OPTS} -jar ${base_dir}/${JAR_NAME} ${SPRING_CONFIG_OPTS}"
  if [ "x$DAEMON_MODE" = "xtrue" ]; then
    nohup $COMMAND >/dev/null 2>&1 & echo $COMMAND' >/dev/null 2>&1 &'
  else
    echo $COMMAND & exec $COMMAND
  fi
  findPid
  if [ "x${PIDS}" = 'x' ]; then
    echo error! ${JAR_NAME} not running!!!!!
  else
    echo "start finish PID: $PIDS"
  fi

elif [ "$1" = "stop" ] ; then
  findPid
  if [ "x${PIDS}" != 'x' ]; then
    echo pid: $PIDS
    echo kill ${JAR_NAME}
    kill ${PIDS}
    echo 'stopping...'
    while [ -n "$PIDS" ]; do
      sleep 3
      findPid
    done
    echo stop finished.
  else
    echo ${JAR_NAME} not running!!!!!
  fi
elif [ "$1" = "status" ] ; then
  findPid
  if [ "x${PIDS}" = 'x' ]; then
    echo ${JAR_NAME} not running
  else
    echo ${JAR_NAME} is running: ${PIDS}
    echo '-----------------------------------'
    ps ax | grep -i "${base_dir}/${JAR_NAME}" | grep java | grep -v grep
  fi
else
    printf "Usage: $0 <command>
  Optional command:
       start             Start Service
       stop              Stop Service, waiting up to 3 seconds for the process to end
       status            show Service status
"
fi