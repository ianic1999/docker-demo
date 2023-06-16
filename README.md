# Docker-Demo Application

Spring Boot application that allows users to manage football teams and players. This document provides an overview of the application and the steps how to run it using Docker.

## Application Structure

The application follows a typical Spring Boot project structure:

```
dockerapp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.dockerapp/
|   |   |       ├── config/
│   │   │       ├── controller/
│   │   │       ├── domain/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── DockerappApplication.java
│   │   └── resources/
│   │       ├── images/
│   │       └── application.properties
│   └── test/
├── Dockerfile
├── docker-compose.yaml
└── pom.xml
```

The main components of the application are as follows:

- `controller/`: Contains the controllers responsible for handling HTTP requests.
- `domain/`: Includes the entity classes representing football teams and players.
- `repository/`: Provides the interfaces for database operations.
- `service/`: Contains the business logic and services for managing teams and players.
- `resources/images/`: Directory for storing team logos.

## Image Upload and Storage

The application allows users to upload team logos as images. The logos are stored in the `resources/images/` directory. The application serves these images statically, and they can be accessed at the `/images/**` path.
Configuration for serving static files is:
```
@Configuration
public class StaticResourcesConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///app/src/main/resources/images/")
                .setCachePeriod(0);
    }
}

```

## Building and Running the Application

To build and run the application, follow these steps:

1. Ensure you have Docker installed on your system.
2. Clone the project from the repository.
3. Open a terminal and navigate to the project directory.

We have 2 approaches to run the application: using Docker and using Docker-Compose. In both cases, we will need a **named volume** to persist uploaded images.

a) **Using Docker**

We have the following Dockerfile at the root of our project:

```
FROM maven:3.8.6-openjdk-11 as builder
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean install

FROM openjdk:11
COPY --from=builder app/target/dockerapp-0.0.1-SNAPSHOT.jar ./dockerapp.jar
EXPOSE 8080
CMD ["java", "-jar", "./dockerapp.jar"]

```

1. Build the docker image: `docker build -t dockerapp . `
2. Run docker container: `docker run -p 8080:8080 -e DATABASE_HOST=<host> -e DATABASE_PORT=<port> -e DATABASE_NAME=<name> -e DATABASE_USER=<user> -e DATABASE_PASSWORD=<passwrod> -v images:/app/src/main/resources/
images dockerapp`. Give the appropriate values to environment variables for connecting to your database.

b) **Using Docker-Compose**

We have the following docker-compose.yaml file to the root of our project:

```
version: "3.8"
services:
  backend:
    build: ./
    ports:
      - "8080:8080"
    volumes:
      - images:/app/src/main/resources/images
    environment:
      DATABASE_HOST: "host.docker.internal"
      DATABASE:PORT: 5432
      DATABASE_NAME: dockerapp_db
      DATABASE_USER: postgres
      DATABASE_PASSWORD: postgres

volumes:
  images:

```
1. Run the docker-compose file: `docker-compose up`. Docker-Compose provides the needed configuration for running the container, such as environment variables, port mapping or named volumes.

The application will be accessible at `http://localhost:8080`.

## Conclusion

This document provided an overview of how you can run your Spring Boot application using Docker. We mentioned 2 approaches: Docker and Docker-Compose, it's up to you which one is more suitable. Also, we used named volumes in order to persist the uploaded files, so they can survive container restart and not be lost.
