# Microservices Mini Project
This project is made just to demostrate the use of some tech stacks such as Spring with Java, Springboot Config server, Eureka, Spring Security with oauth and JWT, Spring Gateway, Zipkin and RabbitMQ.
## Requirements
- Java 8
- Docker
- Gradle
## Installation
Clone the repo in your server or local machine.
```bash
git clone git@gitub.com:HectorCeja/microservices.git
```
## Environment
### Builds
To get all environment done you have to follow next steps
#### Move to root folder and execute runAll script to generate all microservice jars
#### Linux/Mac
```bash
./runAll.sh
```
#### Windows
```bash
runAll.bat
```
### Local images creation
Here we need to run next script for local images
#### Windows
```bash
createAllImages.bat
```
### Pulling images from docker hub
After the builds, we need to create the images, first, we need to pull from docker hub these 3 images:
```bash
docker pull mysql:8
docker pull postgres:12alpine
docker pull rabbitmq:3.11-mamagement-apline
```
Now we must create the docker network to communicate between containers
```bash
docker network create microservices-project
```
After pull and network creation, we need to run docker compose only for those 3 images to create their containers:
```bash
cd docker-compose/
docker-compose up -d mysql8-service
docker-compose up -d postgresql-12-service
docker-compose up -d rabbitmq11-service
```
Next, we can create two more containers in the same folder, config-server for configuration variables and eureka server for balancing
```bash
docker-compose up -d config-server
docker-compose up -d eureka-service
```
### Zipkin with MySQL
Next thing we need to do is open workbench or mysql client to create zipkin database, once created we must create the user zipkin with password zipkin, then you need to add a privilege to this user, go to `User and privileges` where you created the user and select the `Schema Privileges` option, `Add Entry` then select the schema option and select zipkin, finally check all `Object Rights` permissions and save everything.
#####
Now look for zipkinDDL.sql in root folder and run the sql commands in MySQL, once you done all this, now is time to create the zipkin image. First you need to download zipkin jar from [HERE](https://zipkin.io/pages/quickstart.html) in Java section and ``latest release`` link.
####
Move the jar to zipkin-server folder and now you can run the docker image and its container with this:
```bash
cd zipkin-server/
docker build -t zipkin-service:v1
cd ../docker-compose/
docker-compose up -d zipkin-service
```
### Last Images
Now we need to create the containers in the next order because some ones depends on others:
#####
First two instances of products to validate load balancing
```bash
cd ../docker-compose/
docker-compose up -d products-service
docker-compose up -d products-service2
```
Then run items and user images:
```bash
cd ../docker-compose/
docker-compose up -d users-service
docker-compose up -d items-service
```
You can track the logs by running:
```bash
docker-compose logs -f
```
And verify status with `docker ps`, if status is up, everything is fine
####
Finally you need to create the last images. In docker-compose folder:
```bash
docker-compose up -d oauth-service
docker-compose up -d gateway-service
```
## Usage
### Eureka
You can validate the images in eureka by going to your [LOCALHOST](localhost:8761) localhost:8761. Here you can see some services with their Ids.
### RabitMQ
You can validate the connection in rabbit by going to your [LOCALHOST](localhost:15672) localhost:15672. Here you define your consumer services and producers and their connections.
### Zipkin UI
in [ZIPKIN](localhost:9411) localhost:9411 you can find the traceability from your services, their routes or track errors you can follow the entire path from the start to the end.
### Gateway
every endpoint should use 8090 as port. In this port we use the gateway for validations, security, filters and balancing.
### Postman
Here in postman we can try the endpoints from products, items, users, for example an endpoint of each of them:
#### Items
GET localhost:8090/api/items/list
#### Products
GET localhost:8090/api/products/list
#### Users
GET localhost:8090/api/users/users/
## Security
To test the security with token we must create the token first by using the next endpoint
#### Oauth
POST localhost:8090/api/security/oauth/token
####
Authorization tab -> Basic Auth: frontendapp : 12345
####
In body tab we need to select x-www-form-urlencoded and add these 3 keys:
####
```
username : admin
password : 12345
grant_type : password
```
Now copy the access_token value and go and try any put or post endpoint from another microservice, an example:
####
PUT localhost:8090/api/items/edit/1
```
{
  “name”: “testing”,
  “price”: 23333.0,
  “createAt”: “2022-10-13”
}
```
You will get an unauthorized error, because the user must be validated, for this we need to add the access_token in Authorization tab, and select Bearer Token, and paste the token, now try again and you will update the object.
### Traceability information
To track and trace more information between micro services, you can consult in mySQL database, go to your db client and use zipkin database, now execute one or all following queries
```
SELECT * FROM zipkin_spans;
SELECT * FROM zipkin_annotations;
SELECT * FROM zipkin_dependencies;
```
## Unit Testing
Work in progress
## Troubleshooting
### Ports
If you have problems with ports, kill the ports usages and then try again to create the containers

## Other docker compose commands
To disable a compose 
```
docker-compose down -v
```
Check logs
```
docker-compose logs -f
```
Docker stop containers
```
docker stop $(docker ps -q)
```
Docker remove all stopped containers
```
docker rm $(docker ps -a -q)
```
## License
[LICENSE](https://google.com)
