FROM postgres:16
MAINTAINER Durian Sosa <dmsosa21@outlook.com>

#create our custom pg image, just using a default postgres image but creating the schemas for our microservices

ENV POSTGRES_PASSWORD=password \
    POSTGRES_USER=dmsosa

RUN apt-get update && apt-get dist-upgrade -y --force-yes && \
    apt-get install dos2unix && \
    apt-get autoremove -y && apt-get clean && \
    rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/*

COPY pg-entrypoint.sh ./docker-entrypoint-initdb.d/

RUN cd ./docker-entrypoint-initdb.d && chmod 755 ./pg-entrypoint.sh && \
    dos2unix -n ./pg-entrypoint.sh ./pg-entrypointx.sh && \
    chmod 755 ./pg-entrypointx.sh


EXPOSE 5000:5432

