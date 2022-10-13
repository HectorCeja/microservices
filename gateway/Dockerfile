FROM openjdk:12
VOLUME /tmp
ADD ./build/libs/gateway-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "/gateway-service.jar"]
