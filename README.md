# microservices project

###Important
####Download zipkin jar from its official link and place it in root project
####https://zipkin.io/pages/quickstart.html

#ZIPKIN
Use workbench for this 
####To enable zipkin you must create the db in MySQL, the name must be zipkin, 
####then create the user zipkin with password zipkin
####next you should use zipkinDDL.sql from root, and run it in mysql workbench
#### finally, you must add the privilege to zipkin db, go to user and privileges and then schema privileges, add entry, then select the schema and finally check all the permissions from Object Rights
####Now go to Docker section and create zipkin image

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

docker build -t eureka-service:v1 .
docker run -p 8761:8761 --name eureka-service --network microservices-project -d eureka-service:v1

docker build -t products-service:v1 .
docker run -P --name products-service --network microservices-project -d products-service:v1

docker build -t gateway-service:v1 .
docker run -p 8090:8090 --name gateway-service --network microservices-project -d gateway-service:v1

docker build -t users-service:v1 .
docker run -P --name users-service --network microservices-project -d users-service:v1

docker build -t items-service:v1 .
docker run -P --name items-service --network microservices-project -d items-service:v1

docker build -t oauth-service:v1 .
docker run -p 9100:9100 --name oauth-service --network microservices-project -d oauth-service:v1

docker build -t zipkin-service:v1 .
docker run -p 9411:9411 --name zipkin-service --network microservices-project -e RABBIT_ADDRESSES=rabbitmq11-service:5672 -e STORAGE_TYPE=mysql -e MYSQL_USER=zipkin -e MYSQL_PASS=zipkin -e MYSQL_HOST=mysql8-service -d zipkin-service:v1

docker pull mysql:8
docker run -p 3306:3306 --name mysql8-service --network microservices-project -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=db_springboot_cloud -d mysql:8

docker pull postgres:12-alpine
docker run -p 5433:5432 --name postgresql-12-service --network microservices-project -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=db_springboot_cloud -d postgres:12-alpine

docker pull rabbitmq:3.11-management-alpine
docker run -p 15672:15672 -p 5672:5672 --name rabbitmq11-service --network microservices-project -d rabbitmq:3.11-management-alpine

#Escalate docker instances
###Just run  docker run -P --network microservices-project -d products-service:v1

#docker compose commands
####to disable a compose ->  docker-compose down -v
####to run an specific image -> docker-compose  up -d mysql8-service
####docker-compose logs -f

#docker stop containers
####docker stop $(docker ps -q)
####docker rm $(docker ps -a -q)
