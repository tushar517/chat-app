FROM openjdk:17-jdk-slim
COPY --from= build /build/libs/Chat-App-1.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]