#!/bin/bash

source "`dirname "$0"`"/service-env.sh

if [ "$1" = "start" ] ; then
  systemctl start $SERVICE_NAME.service
elif [ "$1" = "stop" ] ; then
  systemctl stop $SERVICE_NAME.service
elif [ "$1" = "status" ] ; then
  systemctl status $SERVICE_NAME.service
elif [ "$1" = "restart" ] ; then
  systemctl restart $SERVICE_NAME.service
else
    printf "Usage: $0 <command>
  Optional command:
       start             systemctl start
       stop              systemctl stop
       restart           systemctl restart
       status            systemctl status
"
fi