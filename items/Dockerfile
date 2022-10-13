FROM openjdk:12
VOLUME /tmp
ADD ./build/libs/items-0.0.1-SNAPSHOT.jar items-service.jar
ENTRYPOINT ["java", "-jar", "/items-service.jar"]
