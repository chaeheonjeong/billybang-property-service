FROM openjdk:17
ARG JAR_FILE=target/*.jar
VOLUME /tmp
COPY ${JAR_FILE} billybang-property-service.jar
EXPOSE 3001
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/billybang-property-service.jar"]