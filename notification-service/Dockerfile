FROM openjdk:21
WORKDIR /app
COPY target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=docker
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]