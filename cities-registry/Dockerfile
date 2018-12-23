FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/docker/cities-registry.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8081