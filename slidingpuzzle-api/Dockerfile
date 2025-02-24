# Use OpenJDK 17 as the base image
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the entire springboot project into the container
COPY . .

# Give execution permission to the Maven wrapper
RUN chmod +x ./mvnw

# Build the springboot application
RUN ./mvnw clean package -DskipTests

# Find the generated JAR file dynamically
RUN mv target/*.jar app.jar

# Expose port 9000 for the Spring Boot application
EXPOSE 9000

# Run the application
CMD ["java", "-jar", "app.jar"]