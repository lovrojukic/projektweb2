# Step 1: Build the application JAR
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Create the runtime container
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/jedan-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
