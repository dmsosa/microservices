FROM postgres:16
MAINTAINER Durian Sosa <dmsosa21@outlook.com>

#create our custom pg image, just using a default postgres image but creating the schemas for our microservices

ENV POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
    POSTGRES_USER=$POSTGRES_USER \
    SCHEMA=$SCHEMA \
    POSTGRES_DB=$POSTGRES_DB

COPY ./$SCHEMA ./docker-entrypoint-initdb.d/
COPY ./pg-entrypoint.sh ./

RUN apt-get update && apt-get dist-upgrade -y --force-yes && \
    apt-get install dos2unix && \
    apt-get autoremove -y && apt-get clean && \
    dos2unix -n ./pg-entrypoint.sh ./docker-entrypoint-initdb.d/pg-entrypointx.sh && \
    chmod 755 ./docker-entrypoint-initdb.d/pg-entrypointx.sh && \
    rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["./docker-entrypoint-initdb.d/pg-entrypointx.sh"]

