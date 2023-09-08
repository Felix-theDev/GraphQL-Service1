# Use a base image with Java and Spring Boot
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/your-service.jar .

# Define the command to run your application
CMD ["java", "-jar", "your-service.jar"]
