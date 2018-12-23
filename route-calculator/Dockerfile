FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/docker/route-calculator.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8081