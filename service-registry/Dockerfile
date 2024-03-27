FROM openjdk:21

COPY ./target/*.jar /app/
ENTRYPOINT ["java", "-jar", "/app/service-registry-0.0.1-SNAPSHOT.jar"]