FROM openjdk:12
VOLUME /tmp
EXPOSE 9100
ADD ./build/libs/oauth-0.0.1-SNAPSHOT.jar oauth-service.jar
ENTRYPOINT ["java", "-jar", "/oauth-service.jar"]
