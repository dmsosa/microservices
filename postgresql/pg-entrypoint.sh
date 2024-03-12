#!/bin/bash

echo env vars: $POSTGRES_USER, $POSTGRES_PASSWORD, $SCHEMA


psql -v ON_ERROR_STOP=1 -U $POSTGRES_USER <<-EOSQL
    CREATE USER dmservices;
    CREATE DATABASE accountdb;
    GRANT ALL PRIVILEGES ON DATABASE accountdb TO dmservices;
EOSQL
echo account database created succesfully