FROM openjdk:21 as builder
WORKDIR app
COPY ./target/config-server-0.0.1-SNAPSHOT.jar ./app/
RUN java -Djarmode=layertools -jar ./app/config-server-0.0.1-SNAPSHOT.jar extract

FROM openjdk:21

WORKDIR app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

HEALTHCHECK CMD curl -f http://localhost:8888/actuator/health || exit 1

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8888