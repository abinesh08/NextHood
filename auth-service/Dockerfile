FROM openjdk:21
WORKDIR /app
COPY target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]