FROM openjdk:21 as builder

WORKDIR /app
COPY /target/*.jar .
RUN java -Djarmode=layertools -jar ./service-registry-0.0.1-SNAPSHOT.jar extract

FROM openjdk:21

WORKDIR /app

COPY --from=builder ./app/dependencies/ ./
COPY --from=builder ./app/spring-boot-loader/ ./
COPY --from=builder ./app/snapshot-dependencies/ ./
COPY --from=builder ./app/application/ ./

HEALTHCHECK CMD curl --fail http://localhost:8761/actuator/health || exit 1
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8761