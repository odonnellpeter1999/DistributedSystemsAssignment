# Distributed Imagination - Distributed Delivery Management System

![Diagram of System Architechture](./system-diagram.png)

A microservices system that offers an 'inbetween' for e-commerce sites and postal services. Our system agregates a number of postal services and offers an interface from which you can:

1. Request and compare quotations for a partical delivery order 
2. Initiate a delivery order
3. Track that orders status and location in the postal services sorting centres.

This system is easily scalable to accommodate multiple postal services and designed to perform under load from its clients.

## Running the system locally

### Running via docker-compose

First, from the root of the repository, install all dependencies and build the jars with maven:
```
mvn clean install
```

Then you can build the docker images and finally, run them:
```
docker-compose build
docker-compose up
```

### Running via kubernetes
A single node cluster to run kubernetes locally. Follow the guide to install all required packages
```
https://kubernetes.io/docs/tasks/tools/
```


<br>
<br>
Gitlab container registry used to store images.


On Gitlab, navigate to Settings>Access Tokens and create a token with `read_registry` scope.
Take note of this token and keep it safe! We will need again.


<br>
Login to registry, you will be prompted to enter username, password 
<br>
<br>

`username, can be found on gitlab profile page with the @ prefix`

`password, token generated from the last step`

```
docker login registry.gitlab.com
```

On a successful login, you should see a message similar to the below:
```
Authenticating with existing credentials...
Login Succeeded
```

Alternatively, you may also see the below message 
if a credential helper was not found during login.

```
Authenticating with existing credentials...
WARNING! Your password will be stored unencrypted in /home/dev/.docker/config.json.
Configure a credential helper to remove this warning. See
https://docs.docker.com/engine/reference/commandline/login/#credentials-store

Login Succeeded
```



kubernetes credentials
```
kubectl create secret docker-registry regcred --docker-server=registry.gitlab.com --docker-username=<your_username> --docker-password=<your_access_token> --docker-email=<your_email>
```


Building

```
minikube start
minikube addons enable ingress
kubectl apply -f k8s/
```

Will give back the address and port to reach the service via browser
```
minikube service quotation-service-service
```
<br>
<br>

### Running the sample client

