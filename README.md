# city-route-calculator

This system is provided by two main micro-services. Both written in Java with Spring Boot framework.

Both microservices are projected to be a docker image, but you can also start in a local way for better
debbugging and fast response.

You can find two main subfolders here, each one being a separated micro-service.

- cities-registry
- route-calculator

## Running in docker with docker-compose

To start using the docker-compose.yaml you must run the following commands:

### Accessing the microservices (in docker environment)

## Running without docker

Follow the below instructions to start each microservice in a local way.
In this way you can debug more easily and test a local configurations in your environment.
This is intended to have a better and a fast way to develop the micro-services, but
the main intention is to run it in a dockerized way. If you want please see the section above
of this one explaining on how to run in docker.

### Building and running

To build you must be on a environment with JDK8 installed. This is the only pre-requisite.
Take note that if you are using Windows environment, the initial executable ./gradlew must be
run as gradlew.bat

You will only be able to run the route-calculator micro-service if you also starts the cities-registry.
Too see how you can access API documentations or the services itself, please reach the accessing the 
micro-services section.

#### cities-registry (local build)

```bash
cd cities-registry
./gradlew bootRun
```

#### route-calculator (local build)

```bash
cd route-calculator
./gradlew bootRun
```

### Accessing the micro-services (local)

To access each micro-service you can access directly in your browser. But if you
want to test the API is strongly recommended that you have a tool like Postman or
Insomnia to test the provided REST API services.

#### cities-registry (local access)

To see all the available usage, please refer to the Swagger Documentation API.
You can insert, save, list Cities and Connections between the cities using this
microservice.

Currently this micro-service is using H2 Database as a local database but this
is intented to be changed on a production environment.

##### cities-registry access the API

- [http://localhost:8081/connections/](http://localhost:8081/connections/)
- [http://localhost:8081/cities/](http://localhost:8081/cities/)

##### cities-registry access the Documentation API (Swagger UI Interface)

- [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

#### route-calculator (local access)

To see all the available usage, please refer to the Swagger Documentation API

##### route-calculator access the API

- [http://localhost:8080/calculate-route/](http://localhost:8080/calculate-route/)

##### route-calculator access the Documentation API (Swagger UI Interface)

- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)