# Use official OpenJDK 17 image as base
FROM openjdk:17-jdk-slim

# Set working directory in container
WORKDIR /app

# Copy Maven wrapper and pom.xml first (to leverage Docker cache)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give execute permission to mvnw
RUN chmod +x mvnw

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the source code
COPY src ./src

# Build the app without running tests
RUN ./mvnw clean package -DskipTests

# Expose port (adjust if you use another port)
EXPOSE 8080

# Run the jar file (replace with your jar name if different)
CMD ["java", "-jar", "target/NoteSample-0.0.1-SNAPSHOT.jar"]
