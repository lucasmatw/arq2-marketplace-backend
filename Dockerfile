FROM openjdk:17-jdk-alpine
VOLUME /tmp

ADD target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]
