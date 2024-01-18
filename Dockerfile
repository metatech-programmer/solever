# Use a minimal JDK Alpine image as the base image
FROM eclipse-temurin:17-jdk-alpine as build

# Set the working directory in the container
WORKDIR /workspace/app

# Copy Maven wrapper and project POM file
COPY pom.xml .

# Copy the source code
COPY src src

# Build the application
RUN ./mvnw clean install -DskipTests

# Create a new stage for the final image
FROM eclipse-temurin:17-jre-alpine

# Set the working directory in the final image
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /workspace/app/target/*.jar app.jar

# Expose the port on which the application will run
EXPOSE 8080

# Define the entry point for the application
ENTRYPOINT ["java", "-jar", "app.jar"]
