FROM openjdk:21 as builder
WORKDIR app
COPY ./target/auth-service-0.0.1-SNAPSHOT.jar ./app/
RUN java -Djarmode=layertools -jar ./app/auth-service-0.0.1-SNAPSHOT.jar extract

FROM openjdk:21

WORKDIR app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

HEALTHCHECK --start-period=10s CMD curl --fail http://localhost:9000/api/uaa/actuator/health || exit 1
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 9000