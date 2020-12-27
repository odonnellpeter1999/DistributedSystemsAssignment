# Eureka Server

### /postal-services endpoint
The /postal-services endpoint provides a mapping of all registered postal services. This can be used by broker services
route their requests to all postal services. 

Postal services will only appear at this endpoint if their `spring.application.name` property in the `application.properties` 
file contains 'POSTAL-SERVICE'.

### Registering with the discovery server
Add the following dependency to your projects `pom.xml`...
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```
Add the following repository for the eureka-client dependency...
```
<repositories>
    <repository>
        <id>spring-milestones</id>
        <name>Spring Milestones</name>
        <url>https://repo.spring.io/milestone</url>
    </repository>
</repositories>
```

Also, add the following dependencyManagement...
```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement
```
Make sure the spring cloud version is defined in the `<properties>` tags e.g. `<spring-cloud.version>2020.0.0-M6</spring-cloud.version>`

Finally, Add the following line to the projects `resources/application.properties` file
```
eureka.client.serviceUrl.defaultZone=${EUREKA_SERVER:http://localhost:8761/eureka}
```

### Containerizing the project
Create a simple `Dockerfile`, something like...
```
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/*.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
```
Add the following plugin to the projects `pom.xml` to automatically build the docker image when you do a `mvn install`
```
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>dockerfile-maven-plugin</artifactId>
    <version>1.4.9</version>
    <executions>
        <execution>
            <id>default</id>
            <goals>
                <goal>build</goal>
                <goal>push</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <repository>distributed_imagination/${project.artifactId}</repository>
        <tag>${project.version}</tag>
        <buildArgs>
            <JAR_FILE>${project.build.finalName}.jar</JAR_FILE>
        </buildArgs>
    </configuration>
</plugin>
```
In the `docker-compose.yml` add the new service being sure to specify the `DEUREKA_SERVER` option.

E.g.
```
demo-service:
    image: distributed_imagination/demo-service:0.0.1-SNAPSHOT
    environment:
      - JAVA_OPTS=
        -DEUREKA_SERVER=http://discovery:8761/eureka
    depends_on:
      - discovery
    ports:
      - 8080:8080
```
