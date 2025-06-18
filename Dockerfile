# ---------- Stage 1: Build the application ----------
FROM gradle:8.2.1-jdk17 AS builder

WORKDIR /app

COPY . /app/

RUN ./gradlew clean build
RUN ./gradlew bootJar --no-daemon

# ---------- Stage 2: Run the application ----------
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
