# Eureka Server

## How To Run

- Run server with default parameters: `mvn spring-boot:run -pl server`
- Run example client with default parameters: `mvn spring-boot:run -pl client-example`
- Override (some) default parameters:
  - `mvn spring-boot:run -pl client-example -Dspring-boot.run.arguments="--server.port=8762"`

## Default Parameters

Default parameters are listed in respective application.properties files.

- Server Port: 8761 (server.port)
- Client Port: 8081
- Server URI (used by client): http://localhost:8761/eureka (eureka.client.serviceUrl.defaultZone)

## How To Use

- Once the Eureka server is up and running you can access its interface at `http://localhost:8761/`
- Ensure the Eureka server is up and running before starting the client
- Client has a /greeting route accessible at `http://localhost:8081/greeting`
- Client has route to retrieve all known registered applications accessible at `http://localhost:8081/service-instances`
- Client has route to retrieve specific instances of registered application accessible at `http://localhost:8081/service-instances/{applicationName}`
  - e.g. `/service-instances/client-example`
