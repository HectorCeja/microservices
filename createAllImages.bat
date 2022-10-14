(
ECHO Creating Config server image
cd config-server/
docker build -t config-server:v1 .
ECHO Docker image has been created
cd ..
)&(
cd eureka/
ECHO Doing Eureka Image
docker build -t eureka-service:v1 .
ECHO Docker image has been created
cd..
)&(
cd gateway/
ECHO Doing Gateway Image
docker build -t gateway-service:v1 .
ECHO Docker image has been created
cd..
)&(
cd items/
ECHO Doing Items Image
docker build -t items-service:v1 .
ECHO Docker image has been created
cd..
)&(
cd oauth/
ECHO Doing Oauth Image
docker build -t oauth-service:v1 .
ECHO Docker image has been created
cd..
)&(
cd products/
ECHO Doing Products Image
docker build -t products-service:v1 .
ECHO Docker image has been created
cd..
)&(
cd users/
ECHO Doing Users Image
docker build -t users-service:v1 .
ECHO Docker image has been created
cd..
ECHO ALL IMAGES ARE BEEN CREATED..
)