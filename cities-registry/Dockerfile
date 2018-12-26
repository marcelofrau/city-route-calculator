FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/docker/cities-registry.jar app.jar
COPY keystore-city-registry.p12 keystore-city-registry.p12
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8444