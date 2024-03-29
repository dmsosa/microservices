FROM postgres:16
MAINTAINER Durian Sosa <dmsosa21@outlook.com>

#create our custom pg image,
#just using a default postgres image but creating the schemas for our microservices

ENV POSTGRES_USER=${POSTGRES_USER}\
    POSTGRES_PASSWORD=${POSTGRES_PASSWORD}

COPY ./pg-entrypoint.sh ./docker-entrypoint-initdb.d/

#pg-entrypoint has CRLF line separators by default since it is written in windows, whit this run command
#we create an identical file pg-entrypointx.sh with LF line separators, so that it can
#be executed by the container (with Linux OS)
RUN apt-get update && apt-get dist-upgrade -y --force-yes && \
    apt-get install dos2unix && \
    apt-get autoremove -y && apt-get clean && \
    cd docker-entrypoint-initdb.d && \
    dos2unix -n ./pg-entrypoint.sh ./pg-entrypointx.sh && \
    chmod 755 ./pg-entrypointx.sh && \
    rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/* && rm ./pg-entrypoint.sh

#docker-entrypoint-initdb.d will be the default entrypoint, the init script will not be executed if all databases are already created
EXPOSE 5000:5432


