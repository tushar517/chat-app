# ---------- Stage 1: Build the application ----------
FROM gradle:8.2.1-jdk17 AS builder

WORKDIR /home/app

COPY --chown=gradle:gradle . .

RUN gradle clean build --no-daemon

# ---------- Stage 2: Run the application ----------
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=builder /home/app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
