##Running the service

Run the application via CLI with local spring profile

`mvn spring-boot:run -Dspring-boot.run.profiles=local`


<br>


##Lombok
In the entities, Lombok annotations used to generate methods to avoid standard verbose method 
definitions.

<br>

##DB
H2 in-memory db used with this template


<br>

##DB migrations
DB migrations are done via `liquidbase`, https://www.liquibase.org/
 
An initial migration script under `resources/db/migration/`, it runs on the startup of the application.


- Generate changelog automatically

    `mvn liquibase:generateChangeLog`


- Update DB using existing migrations
    `mvn liquibase:update`


<br>

##Endpoints


####Parcels
- Get all parcels related to an orderId

    `http://localhost:8408/parcels?orderId=1afb88b2-fe91-4b12-a5fb-08b37252adfa`

- Get parcel by its id 

    `http://localhost:8408/parcels?parcelId=2b0db21a-44d9-4f68-a277-d0bb681faeda`
    
- Get all parcels

    `http://localhost:8408/parcels`


####Orders
- Get order by its id

    `http://localhost:8408/orders?orderId=1afb88b2-fe91-4b12-a5fb-08b37252adfa`
    
- Get all orders

    `http://localhost:8408/orders`

<br>

##Monitoring, metrics etc.
This is achieved with a package `actuator`

`url:port/actuator` - lists all available endpoints under actuator

`localhost:8408/actuator`

```
{
    "_links": {
        "self": {
            "href": "http://localhost:8400/actuator",
            "templated": false
        },
        "health": {
            "href": "http://localhost:8400/actuator/health",
            "templated": false
        },
        "health-path": {
            "href": "http://localhost:8400/actuator/health/{*path}",
            "templated": true
        },
        "info": {
            "href": "http://localhost:8400/actuator/info",
            "templated": false
        }
    }
}```