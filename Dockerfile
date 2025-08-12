FROM maven:latest AS build
LABEL authors="PeachGB"

WORKDIR /app

COPY pom.xml .

run mvn dependency:go-offline

copy src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-slim

WORKDIR /app

COPY --from=build /app/target/userService-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
