# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the entire springboot project into the container
COPY . .

# Build the springboot application
RUN ./mvnw clean package -DskipTests

# Expose port 9000 for the Spring Boot application
EXPOSE 9000

# Run the application
CMD ["java", "-jar", "target/*.jar"]