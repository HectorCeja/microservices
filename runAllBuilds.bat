(
ECHO Doing Config server build
cd config-server/
gradlew clean build
ECHO config server build has finished
cd ..
)&(
cd eureka/
ECHO Doing Eureka Build
gradlew clean build
ECHO Eureka build has finished
cd..
)&(
cd gateway/
ECHO Doing Gateway Build
gradlew clean build
ECHO Gateway build has finished
cd..
)&(
cd items/
ECHO Doing Items Build
gradlew clean build
ECHO Items build has finished
cd..
)&(
cd oauth/
ECHO Doing Oauth Build
gradlew clean build
ECHO Oauth build has finished
cd..
)&(
cd products/
ECHO Doing Products Build
gradlew clean build
ECHO Products build has finished
cd..
)&(
cd users/
ECHO Doing Users Build
gradlew clean build
ECHO Users build has finished
cd..
ECHO ALL BUILDS ARE DONE..
)