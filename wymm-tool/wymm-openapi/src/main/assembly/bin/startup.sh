#!/bin/bash

base_dir=$(dirname $0)

EXTRA_ARGS=${EXTRA_ARGS-' -name Server -loggc'}
COMMAND=$1

if [ "$1" = "-help" ] ; then
  echo "USAGE:"
  echo "$0                           Start Catalina in a separate window"
  echo "$0 -daemon=false             Start Catalina in the current window"
  echo "$0 -help"
  exit 1
elif [ "$1" = "-daemon=false" ] ; then
  exec $base_dir/service.sh start $EXTRA_ARGS
else
  EXTRA_ARGS="-daemon "$EXTRA_ARGS
  exec $base_dir/service.sh start $EXTRA_ARGS
fi