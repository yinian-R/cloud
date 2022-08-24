#!/bin/bash

source "`dirname "$0"`"/service-env.sh

if [ -r "/etc/systemd/system/$SERVICE_NAME.service" ]; then
  systemctl stop $SERVICE_NAME.service;
  systemctl disable $SERVICE_NAME.service;
  rm "/etc/systemd/system/$SERVICE_NAME.service"
else
  echo "error: /etc/systemd/system/$SERVICE_NAME.service not exists"
fi
