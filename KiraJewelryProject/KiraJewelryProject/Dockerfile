# Build stage
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/KiraJewelry-0.0.1-SNAPSHOT.jar kirajewelry.jar
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "kirajewelry.jar"]