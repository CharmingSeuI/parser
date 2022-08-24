#!/bin/bash

file_name=$1
echo "fileName=${file_name}" > ./src/main/resources/fileName.properties
./gradlew bootRun
