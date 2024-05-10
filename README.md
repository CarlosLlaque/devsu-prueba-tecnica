# Spring Bank Example
A toy project that uses the following technologies:
* spring boot
* spring webflux
* spring data r2dbc
* spring cloud: eureka, gateway, stream, config
* resilience4j
* testing: junit, mockito, testcontainers
* build: maven, gradle, docker, docker compose
* database: postgresql
* messages: rabbitmq, kafka

## Components
There are 4 microservices
* persona-ms
* cuenta-ms
* movimientoms
* compositems

A service discovery server
* eurekaserver

An authorization server
* autorizationserver

An edge server (gateway)
* edgeserver

## Project initialization
To initialize the project, ensure that both docker and docker compose are installed on your machine. Navigate to the project's root directory and execute the docker-compose.yml file with the following bash command:
```bash
docker compose up -d
```
## Postman
The [Devsu-prueba-tecnica.postman_collection.json](Devsu-prueba-tecnica.postman_collection.json) json file contains a postman collection to test the microservices' endpoints.
