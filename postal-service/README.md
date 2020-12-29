# Postal Service

Generalised postal service which registers to an Eureka Discovery server and handles quotation, order and tracking requests.
Adds serviceName and serviceID as MetaData when registering to Discovery server.

## API Documentation

The API documentation is auto-generated and can be accessed through the following routes:

- `/api-docs` - OpenAPI JSON docs
- `/api-docs.yaml` - OpenAPI YAML docs
- `/swagger.html` - Swagger UI docs

## Database

H2 in-memory database is used. Login details are defined in application.properties file. UI accessable through:

- `/h2-console`
- JDBC URL=jdbc:h2:mem:testdb
- User Name=sa
- Password=password

Note: H2-console not available when running dockerised version, access blocked.

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

- server.port=8080
- eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
- DeliveryCostMultiplier=0.69 (used to simulate differences between postal services)
- PostalServiceName=DHL
- PostalServiceId=dhl
- DeliverySpeed=10000 (meters per second, used to calculate expected delivery date)
- SimulationInterval=5000 (ms, interval between simulator runs)
