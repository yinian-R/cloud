#!/bin/bash

base_dir=$(dirname $0)

if [ "$1" = "-help" ] ; then
  echo "USAGE:"
  echo "$0                           Stop Service in a separate window"
  echo "$0 -help"
  exit 1
else
  exec $base_dir/service.sh stop
fi