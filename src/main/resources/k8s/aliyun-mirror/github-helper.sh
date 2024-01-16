#!/bin/bash

for line in $(cat ./k8s-images); do
  DIR=$(echo $line | cut -d ":" -f 1)
  mkdir -p $DIR
  echo $line "created."
  touch $DIR/Dockerfile
  echo "FROM $line" > $DIR/Dockerfile
  echo "$DIR/Dockerfile created."
done