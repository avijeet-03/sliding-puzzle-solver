# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the slidingpuzzle-api/target directory into the container
COPY target/*.jar app.jar

# Expose port 9000 for the Spring Boot application
EXPOSE 9000

# Run the application
CMD ["java", "-jar", "app.jar"]