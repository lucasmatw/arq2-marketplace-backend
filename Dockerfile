FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/mercadoflux.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java", "-jar", "/app.jar"]
