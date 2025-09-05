# Stage 1: Build the application
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app

# Copy Maven wrapper files and set execute permission
COPY ./mvnw ./
COPY ./.mvn ./.mvn
RUN chmod +x ./mvnw

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the project skipping tests and test compilation
RUN ./mvnw clean package -Dmaven.test.skip=true

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
