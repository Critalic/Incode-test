FROM openjdk:11-ea-13-jdk-slim
EXPOSE 8080
ADD target/incode_api.jar incode_api.jar
ENTRYPOINT ["java", "-jar", "/incode_api.jar"]