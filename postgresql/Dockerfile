FROM postgres:16
MAINTAINER Durian Sosa <dmsosa21@outlook.com>

#create our custom pg image, just using a default postgres image but creating the schemas for our microservices

COPY /pg-entrypoint.sh ./
ENV POSTGRES_PASSWORD=123 \
    POSTGRES_USER=dmsosa \
    PGDATA=var/lib/postgresql/data
RUN \
    chmod 755 ./pg-entrypoint.sh && \
    apt-get update && apt-get dist-upgrade -y --force-yes && apt-get install -y dos2unix && \
    dos2unix -n ./pg-entrypoint.sh ./pg-entrypointx.sh && chmod 755 ./pg-entrypointx.sh && \
    apt-get autoremove -y && apt-get clean && \
    rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/* && \
    echo innentry


EXPOSE 5000:5432

ENTRYPOINT ["./pg-entrypointx.sh"]