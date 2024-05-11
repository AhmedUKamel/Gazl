FROM openjdk:17-alpine

WORKDIR /app

COPY target/*.jar application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]