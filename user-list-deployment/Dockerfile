FROM openjdk:13-jdk-alpine
ARG APP_JAR_VERSION
COPY build/dependencies/user-list-app-${APP_JAR_VERSION}.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
