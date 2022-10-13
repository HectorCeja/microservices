# microservices project

###Important
####Download zipkin jar from its official link and place it in root project
####Then run zipkin cmd with -> zipkin.cmd and Enter


#Docker
###To create the image run in its root folder:
####docker build -t config-server:v1 .
###Create a network for all microservices with
#### docker network create microservices-project
###Run the docker image to create the container
#### docker run -p 8888:8888 --name config-server --network microservices-project config-server:v1


#ISSUES WITH PORT IN USE
###In windows user this
####netstat -ano | findstr {PORT}
###Then copy the PID
####taskkill /PID {PID} /F

docker build -t config-server:v1 .
docker run -p 8888:8888 --name config-server --network microservices-project -d config-server:v1

docker build -t products-service:v1 .
docker run -P --name products-service --network microservices-project -d products-service:v1

docker build -t gateway-service:v1 .
docker run -p 8090:8090 --name gateway-service --network microservices-project -d gateway-service:v1

docker build -t eureka-service:v1 .
docker run -p 8761:8761 --name eureka-service --network microservices-project -d eureka-service:v1

docker build -t users-service:v1 .
docker run -P --name users-service --network microservices-project -d users-service:v1

docker pull mysql:8
docker run -p 3306:3306 --name mysql8-service --network microservices-project -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=db_springboot_cloud -d mysql:8

docker pull postgres:12-alpine
docker run -p 5433:5432 --name postgresql-12-service --network microservices-project -e POSTGRES_PASSWORD=sasa -e POSTGRES_DATABASE=db_springboot_cloud -d postgres:12-alpine
