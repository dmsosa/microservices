FROM openjdk:21 as builder
WORKDIR app
COPY ./target/noti-0.0.1-SNAPSHOT.jar ./app/
RUN java -Djarmode=layertools -jar ./app/noti-0.0.1-SNAPSHOT.jar extract

FROM openjdk:21

WORKDIR app
COPY --from=builder app/dependencies/ ./
COPY --from=builder app/spring-boot-loader/ ./
COPY --from=builder app/snapshot-dependencies/ ./
COPY --from=builder app/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

EXPOSE 8084