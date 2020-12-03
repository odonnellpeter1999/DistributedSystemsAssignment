# Spring Boot Zuul Server

Zuul server for Assignment 2. Depends on Netflix Eureka for service discovery.

## How To Run

- To run with default parameters: `mvn spring-boot:run`
- To override (some) default parameters:
  - `mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8762"`
  - `java -jar APPNAME.jar --server.port=8762`

## Default Parameters

Default parameters are listed in the application.properties file (./src/main/resources/application.properties)

- Port: 8762 (server.port)
- Eureka URI: http://localhost:8761/eureka (eureka.client.serviceUrl.defaultZone)

## How To Use

Once the Zuul server is up and running the following usefull actuators are available:

- List of available actuators: `/actuator`
- List of available routes: `/actuator/routes`
- Current status of server: `/actuator/health`
- List of available metrics: `/actuator/metrics`
- Lookup of specific metric: `/actuator/metrics/{requiredMetricName}`

## How It Works

1. Zuul synchronises list of services from Eureka
   - E.g. Multiple Sprintboot services have registered with name `spring.application.name=greetingservice`
2. Client tries to access GreetingsService through Zuul
   - E.g. `http://localhost:8762/greetingservice/greeting`
3. Zuul balances load and dynamically routes client to appropriate service endpoint
4. GreetingsService receives request on endpoint `/greeting`
