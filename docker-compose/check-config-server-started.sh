#!/bin/bash
###
# Script which precisely checks if Config Server is up and running.
# It helps during deployment to stand up in exact order and time
###

apt-get update -y

yes | apt-get install curl

curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)

echo -e "\n\nChecking if Config Server is up and running..."
echo "Result status code: " $curlResult

while [[ ! $curlResult == "200" ]]; do
  >&2 echo "Config server is not up yet!"
  sleep 2
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)
done
echo -e "Config Server is up and running!\n\n "

./cnb/lifecycle/launcher