#!/bin/bash
###
# Script which precisely checks if Kafka Topics are created.
# It helps during deployment to stand up in exact order and time
###

apt-get update -y

yes | apt-get install kafkacat

kafkacatResult=$(kafkacat -L -b kafka-broker-1:9092)

echo -e "\n\nChecking if Kafka Topics are created..."
echo "kafkacat result: " $kafkacatResult

while [[ ! $kafkacatResult == *"twitter-topic"* ]]; do
  >&2 echo "Kafka topic has not been created yet!"
  sleep 2
  kafkacatResult=$(kafkacat -L -b kafka-broker-1:9092)
done

echo -e "Kafka Topics are created!\n\n "

./cnb/lifecycle/launcher