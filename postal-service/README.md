# Postal Service

Generalised postal service which registers to an Eureka Discovery server and handles quotation, order and tracking requests.
Adds serviceName and serviceID as MetaData when registering to Discovery server.

## API Documentation

The API documentation is auto-generated and can be accessed through the following routes:

- `/api-docs` - OpenAPI JSON docs
- `/api-docs.yaml` - OpenAPI YAML docs
- `/swagger.html` - Swagger UI docs

## Databases

Two databases can be used:

- H2 in-memory database (DEFAULT)
  - Default spring-boot profile: `mvn spring-boot:run`
  - DB URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`
- PostgreSQL
  - Run with local profile: `mvn spring-boot:run -Dspring-boot.run.profiles=local`
  - DB URL: `jdbc:postgresql://database:5432/` (use localhost if not run through docker-compose)
  - Username: `postgres`
  - Password: `password`
  - Usefull docker command: `docker run -p 5432:5432 --env-file database.env postgres`

H2 console is also available at `/h2-console` for easy DB access through a web interface. Works for both databases using the above details.
Note that the H2-console not available when running dockerised version, access blocked by default but can be changed in the settings file.

## How To Run

- Run server with default parameters: `mvn spring-boot:run`
- Run dockerised version:
  - `mvn package`
  - `docker run -t -i -p 8080:8080 distributed_imagination/postal-service:0.0.1`
- Override parameters:
  - `mvn spring-boot:run -Dspring-boot.run.arguments="--DeliveryCostMultiplier=0.89 --PostalServiceName=AnPost --PostalServiceId=anp"`

## Simulation of Packages

Simulation of orders works as follows:

- Order is placed, expected delivery time is calculated using the input parameter `DeliverySpeed` (see below, in m/s)
- Simulator runs every `SimulationInterval` ms (see below)
- For each order that has not been delivered (`dateDelivered` property is null)
  - Calculate total delivery duration (expected delivery date - date ordered)
  - Divide this duration into 4 phases
    - Phase 0: order confirmed but still at source location
    - Phase 1: order at sorting facility closest to source location
    - Phase 2: order at sorting facility closest to destination location
    - Phase 3: order delivered
  - Update entity (db) properties accordingly

## Parameters

Default parameters as listed in application.properties:

- server.port=8408
- eureka.client.serviceUrl.defaultZone=${EUREKA_URI1:http://localhost:8761/eureka},${EUREKA_URI2:http://localhost:9002/eureka}
- DeliveryCostMultiplier=0.69 (used to simulate differences between postal services)
- PostalServiceName=DHL
- PostalServiceId=dhl
- DeliverySpeed=10000 (meters per second, used to calculate expected delivery date)
- SimulationInterval=5000 (ms, interval between simulator runs)
