!#bin/bash

su postgres -c pg_ctl initdb
psql -v ON_ERROR_STOP=1 -U dmsosa <<-EOSQL
    CREATE USER myuser;
    CREATE DATABASE mydata;
    GRANT ALL PRIVILEGES ON DATABASE mydata TO myuser;
EOSQL