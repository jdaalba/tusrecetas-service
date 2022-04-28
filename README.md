# tusrecetas-service
***
## Requirements
* JDK 17
* Apache Maven 3.6.3
* Docker
## Run this project
* Execute `docker-compose up` on `./docker` folder. It may fail due to `docker-compose` version, so change `version` from `./docker/docker-compose.yml`. Neo4j will be running, you can access to neo4j through your browser at http://localhost:7474. The login using `(username/password): neo4j/secret`.
* Launch the application using `./mvnw spring-boot:run`. 