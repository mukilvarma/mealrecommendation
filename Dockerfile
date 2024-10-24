# Use OpenJDK 21 as the base image
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY mvnw .
COPY .mvn/wrapper .mvn/wrapper
COPY src ./src

# Ensure mvnw is executable
RUN chmod +x mvnw

# Package the application
RUN ./mvnw package -DskipTests

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "target/mealrecommendation-0.0.1-SNAPSHOT.jar"]
