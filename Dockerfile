# Use OpenJDK 21 as the base image
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Ensure the target directory is clean
RUN rm -rf /app/*

# Copy the Maven project files
COPY pom.xml .
COPY .mvn .
COPY src ./src

# Make the mvnw script executable
RUN chmod +x mvnw

# Package the application
RUN ./mvnw package -DskipTests

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/mealrecommendation-0.0.1-SNAPSHOT.jar"]
