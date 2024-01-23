FROM gradle:7.3.3-jdk17 AS build
WORKDIR /app
COPY . /app/
RUN ./gradlew clean build
RUN ./gradlew bootJar --no-daemon
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]