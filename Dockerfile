FROM ubuntu:latest as build
RUN apt-get update
RUN RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from= build /build/libs/Chat-App-1.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]