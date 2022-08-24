#!/bin/bash

source "`dirname "$0"`"/service-env.sh

if [ -r "/etc/systemd/system/$SERVICE_NAME.service" ]; then
  echo "error: /etc/systemd/system/$SERVICE_NAME.service already exists"
else
  echo -e "[Unit]" > $SERVICE_NAME.service
  echo -e "Description=$SERVICE_NAME" >> $SERVICE_NAME.service
  echo -e "After=network-online.target" >> $SERVICE_NAME.service
  echo -e "Wants=network-online.target" >> $SERVICE_NAME.service
  echo -e "" >> $SERVICE_NAME.service
  echo -e "[Service]" >> $SERVICE_NAME.service
  echo -e "Type=forking" >> $SERVICE_NAME.service
  echo -e "WorkingDirectory=$base_dir" >> $SERVICE_NAME.service
  echo -e "ExecStart=/bin/bash -c \"source /etc/profile && /bin/sh $base_dir/bin/startup.sh\"" >> $SERVICE_NAME.service
  echo -e "ExecStop=/bin/bash -c \"source /etc/profile  && /bin/sh $base_dir/bin/shutdown.sh\"" >> $SERVICE_NAME.service
  echo -e "ExecStartPost=/usr/bin/sleep 5" >> $SERVICE_NAME.service
  echo -e "Restart=on-failure" >> $SERVICE_NAME.service
  echo -e "RestartSec=60s" >> $SERVICE_NAME.service
  echo -e "" >> $SERVICE_NAME.service
  echo -e "[Install]" >> $SERVICE_NAME.service
  echo -e "WantedBy=multi-user.target" >> $SERVICE_NAME.service

  mv "$SERVICE_NAME.service" "/etc/systemd/system/$SERVICE_NAME.service"
  systemctl daemon-reload;
  systemctl start $SERVICE_NAME.service;
  systemctl enable $SERVICE_NAME.service;
  systemctl is-enabled $SERVICE_NAME.service;
fi
