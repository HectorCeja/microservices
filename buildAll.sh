#!/bin/bash

cd config-server/
./gradlew clean build
cd ../eureka
./gradlew clean build