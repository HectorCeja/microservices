#!/bin/bash

cd config-server/
./gradlew clean build
cd ../eureka
./gradlew clean build
cd ../gateway
./gradlew clean build
cd ../items
./gradlew clean build
cd ../oauth
./gradlew clean build
cd ../products
./gradlew clean build
cd ../users
./gradlew clean build
cd ..