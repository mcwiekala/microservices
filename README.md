## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
This project was created to learn microservice concepts.
It uses Twitter Connector to search for tweets with some keywords, pushes it to Kafka cluster and then stores it to Elasticsearch.

RESTful API is based on HATEOAS principles.

GUI is based on Thymeleaf 

![Screenshot](docs/images/dashboard.PNG)

The project is based on 12 factor methodology
https://12factor.net/

####Swagger documentation
Good documented API is crucial to sucessfull comunication across different teams. I decided to use Swagger as a POC of elastic-query-service microservice
You can check it here (remember to change the password!):
http://localhost:8183/elastic-query-service/swagger-ui

####API versioning
	
## Technologies
* Java 11
* Spring Boot
* Spring Cloud
* Kafka
* Elasticsearch
* Thymeleaf
	
## Setup
This project uses Spring Cloud CLI to encrypt configurations using  Java Cryptography Extension (JCE).  

The configurations are stored in separate config repository:
https://github.com/mcwiekala/config-server-repository

This is a public repository because of demonstration purposes (normally it should be private). So there is no any login or password to protect access.

Install SDKMAN (https://sdkman.io/install)

Create 2 password to replace:
* 'your_jce_encryption_password' - stands for encryption password
* 'your_password_here' - stands for password which will be encrypted

and run:
```
sdk install springboot 2.4.0
spring install org.springframework.cloud:spring-cloud-cli:2.2.3.RELEASE
spring encrypt your_jce_encryption_password --key 'pass'
```
Replace passwords in 'bootstrap.yml' in each module.

e.g.
spring.cloud.config.password: '{cipher}g2322df111gfdsfdsfdsfg565w465tydfgdfsgfdg64'


Add ENCRYPT_KEY as an Environment Variable
```
export ENCRYPT_KEY='your_password_here'
```
install it locally using Docker:
```
$ mvnw clean install
$ cd ./docker-compose
$ docker-compose up -d
```

To run kafka and elastic cluster you can run:  
```
docker-compose -f common.yml -f kafka_cluster.yml -f elastic_cluster.yml up -d
```

## Architecture

## License
[MIT](https://choosealicense.com/licenses/mit/)