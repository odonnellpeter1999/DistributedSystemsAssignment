# Postal Service

Generalised postal service which registers to an Eureka Discovery server and handles quotation, order and tracking requests.

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

## How To Run

- Run server with default parameters: `mvn spring-boot:run`
- Override parameters:
  - `mvn spring-boot:run -Dspring-boot.run.arguments="--DeliveryCostMultiplier=0.89 --PostalServiceName=AnPost --PostalServiceId=anp"`

## Default Parameters

Default parameters as listed in application.properties:

- server.port=8080
- eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka
- DeliveryCostMultiplier=0.69 (used to simulate differences between postal services)
- PostalServiceName=DHL
- PostalServiceId=dhl
