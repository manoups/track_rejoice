# Stage 1: Build the application using Gradle
FROM gradle:8.12.1-jdk21 AS build
WORKDIR /app

# Copy the Gradle wrapper and settings
COPY gradlew gradlew.bat settings.gradle build.gradle ./
COPY ./gradle/ gradle/
RUN chmod +x gradlew

# Copy project source code
COPY src/main ./src/main
COPY command/src/main ./command/src/main
COPY command/build.gradle ./command
COPY core/src/main ./core/src/main
COPY core/build.gradle ./core
COPY query/src/main ./query/src/main
COPY query/build.gradle ./query
COPY security/src/main ./security/src/main
COPY security/build.gradle ./security

# Build the project (without running tests)
RUN ./gradlew clean build -x test

# Stage 2: Create a lightweight image to run the application
FROM eclipse-temurin:21-jdk-alpine AS run
WORKDIR /app

# Copy the built application JAR
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
