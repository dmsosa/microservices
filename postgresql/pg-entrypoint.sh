#!/bin/bash

echo env vars: $POSTGRES_USER, $POSTGRES_PASSWORD, $SCHEMA
su postgres -c 'pg_ctl initdb'
#psql -v ON_ERROR_STOP=1 -U $POSTGRES_USER <<-EOSQL
#    CREATE USER IF NOT EXISTS dmservices;
#    CREATE DATABASE mydata;
#    GRANT ALL PRIVILEGES ON DATABASE mydata TO myuser;
#EOSQL
su postgres -c 'pg_ctl -l logfile start'
echo db created bro