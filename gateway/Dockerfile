FROM openjdk:21 as builder
LABEL author="durian"
MAINTAINER Durian Sosa <dmsosa21@outlook.com>

WORKDIR /app
COPY /target/*.jar ./
RUN java -Djarmode=layertools -jar ./gateway-0.0.1-SNAPSHOT.jar extract

FROM openjdk:21

WORKDIR /app

COPY --from=builder ./app/dependencies/ ./
COPY --from=builder ./app/snapshot-dependencies/ ./
COPY --from=builder ./app/spring-boot-loader/ ./
COPY --from=builder ./app/application/ ./

HEALTHCHECK CMD curl --fail http://localhost:8061/actuator/health || exit 1

ENTRYPOINT ["java" , "org.springframework.boot.loader.launch.JarLauncher"]