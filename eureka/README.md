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
- Dummy client has single /greeting route accessible `http://localhost:8081/greeting`
