#!/bin/bash

echo env vars: $POSTGRES_USER, $POSTGRES_PASSWORD

psql -v ON_ERROR_STOP=1 -U ${POSTGRES_USER} <<-EOSQL
        CREATE DATABASE authdb;
        CREATE DATABASE accountdb;
        CREATE DATABASE statsdb;
        CREATE DATABASE notidb;
        GRANT ALL PRIVILEGES ON DATABASE authdb TO dmservices;
        GRANT ALL PRIVILEGES ON DATABASE accountdb TO dmservices;
        GRANT ALL PRIVILEGES ON DATABASE statsdb TO dmservices;
        GRANT ALL PRIVILEGES ON DATABASE notidb TO dmservices;
EOSQL

echo "databases for dmsosa's microservices created succesfully"