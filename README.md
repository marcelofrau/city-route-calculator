# city-route-calculator

[![Build Status](https://travis-ci.org/marcelofrau/city-route-calculator.svg?branch=master)](https://travis-ci.org/marcelofrau/city-route-calculator)
[![Codebeat badge](https://codebeat.co/badges/a1dfb632-3f1c-4164-8561-8556e6a8bee3)](https://codebeat.co/projects/github-com-marcelofrau-city-route-calculator-master)
[![Circle-ci Status](https://img.shields.io/circleci/project/github/marcelofrau/city-route-calculator.svg?logo=circleci)](https://circleci.com/gh/marcelofrau/city-route-calculator)
[![License: GPL v3](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![code java](https://img.shields.io/badge/code-java-ffcc00.svg)](http://java.oracle.com)

This system is provided by two main micro-services. Both written in Java with Spring Boot framework.

Both microservices are projected to be a docker image, but you can also start in a local way for better
debbugging and fast response.

You can find two main subfolders here, each one being a separated micro-service.

- cities-registry
- route-calculator

***NOTE:***
Route-calculator will rely on informations provided from cities-registry, if cities-registry is not
available, route-calculator have a circuit-breaker mechanism that prevent user to have some mistaken
error information, so a default fallback method is triggered returning no path available, when
cities-registry is reachable again, everything starts to work properly again.

## Project details

### Scope and definitions

There are two micro-service in this project, cities-registry and route-calculator.

The cities-registry is responsible to list all available cities and connections
between cities. In this micro-service users can list, insert and delete new cities
and new connections. In each connection user can inform the time to travel
between any connection.

The route-calculator is responsible to calculate a route from any given city to another
and show the info in two ways, it shows the minimum path in connections and also shows
the minimum time to travel from a city to another (it also show the path that is made to
have the minimum time)

### Build and process

This project is using travis to ensure all builds are passing. Also it uses codebeat to have a static
analysis tool to indicate where the code could be improved.

### HTTPS communication

All data is handled with https connections, even between services. The route-calculator have the cities-registry
certificate and validate it to certify that the connection is done with the right certificate, in this way
if some other service try to act as a fake cities-registry, the certificate validation will not allow
the route-calculator to communicate with a 'alien' cities-registry.

### Authentication

To authenticate you can use the user:

- username: user, password: user (that has the role USER)
- username: admin, password: admin (that has the role ADMIN)
- username: route-calculator, password: route-calculator (that has the role USER, but is intended to be used internally in the route-calculator service)

#### The role USER

Users with the role USER can read all the data from all the micro-services, but they have no permission to include or delete any data.

#### The role ADMIN

Users with the role ADMIN can do all the stuff, list, include, delete any data.

## Running in docker with docker-compose

To start using the docker-compose.yaml you must run the following commands:

### Accessing the microservices (in docker environment)

To access each micro-service you can access directly in your browser. But if you
want to test the API is strongly recommended that you have a tool like Postman or
Insomnia to test the provided REST API services.

### Building and running all micro-services (using docker-compose)

To start every micro-service using the docker-compose.yml file, you can do it using the following commands:

```bash
./gradlew dockerBuild
docker-compose build
docker-compose up
```

Two main services will be open, one in a 8443 port and another in 8444, you can check more info below on
how to access each micro-service.

***NOTE***: Please run the commands above in the root folder of this project.

#### cities-registry (docker build)

If you want you can start each service individually in docker. You can achieve that using the following command:

```bash
cd cities-registry
./gradlew dockerRun
```

#### route-calculator (docker build)

If you want you can start each service individually in docker. You can achieve that using the following command:

```bash
cd route-calculator
./gradlew dockerRun
```

### Accessing the micro-services (docker)

***NOTE***: Please replace 192.168.99.100 with your docker ip.

#### cities-registry (docker access)


##### cities-registry access the API (docker access)

- [https://192.168.99.100:8444/connections/](https://192.168.99.100:8444/connections/)
- [https://192.168.99.100:8444/cities/](https://192.168.99.100:8444/cities/)

##### cities-registry access the Documentation API (Swagger UI Interface, docker access)

- [https://192.168.99.100:8444/swagger-ui.html](https://192.168.99.100:8444/swagger-ui.html)

#### route-calculator (docker access)

To see all the available usage, please refer to the Swagger Documentation API

##### route-calculator access the API (docker access)

- [https://192.168.99.100:8443/calculate-route/](https://192.168.99.100:8443/calculate-route/)

##### route-calculator access the Documentation API (Swagger UI Interface, docker access)

- [https://192.168.99.100:8443/swagger-ui.html](https://192.168.99.100:8443/swagger-ui.html)

## Running without docker (locally)

Follow the below instructions to start each microservice in a local way.
In this way you can debug more easily and test a local configurations in your environment.
This is intended to have a better and a fast way to develop the micro-services, but
the main intention is to run it in a dockerized way. If you want please see the section above
of this one explaining on how to run in docker.

### Building and running (local)

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

##### cities-registry access the API (local access)

- [https://localhost:8444/connections/](https://localhost:8444/connections/)
- [https://localhost:8444/cities/](https://localhost:8444/cities/)

##### cities-registry access the Documentation API (Swagger UI Interface, local access)

- [https://localhost:8444/swagger-ui.html](https://localhost:8444/swagger-ui.html)

#### route-calculator (local access)

To see all the available usage, please refer to the Swagger Documentation API

##### route-calculator access the API

- [https://localhost:8443/calculate-route/](https://localhost:8443/calculate-route/)

##### route-calculator access the Documentation API (Swagger UI Interface)

- [https://localhost:8443/swagger-ui.html](https://localhost:8443/swagger-ui.html)

## Ideal pipeline

In this section I described an ideal pipeline configuration to achieve the best performance
on development and on distribution of each release.

//TODO

## References

- [https://www.baeldung.com/spring-cloud-netflix-hystrix](https://www.baeldung.com/spring-cloud-netflix-hystrix)
- [http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html](http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html)
- [https://www.drissamri.be/blog/java/enable-https-in-spring-boot/](https://www.drissamri.be/blog/java/enable-https-in-spring-boot/)
- [https://www.opencodez.com/java/read-x509-certificate-java-keystore.htm](https://www.opencodez.com/java/read-x509-certificate-java-keystore.htm)
- [http://shengwangi.blogspot.com/2017/03/spring-resttemplate-useful-hints.html](http://shengwangi.blogspot.com/2017/03/spring-resttemplate-useful-hints.html)
- [https://www.drissamri.be/blog/java/enable-https-in-spring-boot/](https://www.drissamri.be/blog/java/enable-https-in-spring-boot/)
- [https://www.devglan.com/online-tools/bcrypt-hash-generator](https://www.devglan.com/online-tools/bcrypt-hash-generator)
- [https://www.devglan.com/spring-security/spring-boot-security-rest-basic-authentication](https://www.devglan.com/spring-security/spring-boot-security-rest-basic-authentication)
