FROM maven:3.8.6-openjdk-11 as builder
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean install

FROM openjdk:11
COPY --from=builder app/target/dockerapp-0.0.1-SNAPSHOT.jar ./dockerapp.jar
EXPOSE 8080
CMD ["java", "-jar", "./dockerapp.jar"]
