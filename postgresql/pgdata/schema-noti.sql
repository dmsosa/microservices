CREATE TYPE currency as ENUM ('EUR', 'GBP', 'USD');

CREATE TYPE period as ENUM ('HOUR', 'DAY', 'WEEK', 'MONTH', 'QUARTER', 'YEAR');

CREATE TABLE IF NOT EXISTS 'accounts' (
    name VARCHAR[40] NOT NULL UNIQUE PRIMARY KEY,
    last_seen DATE,
    saving_id INTEGER REFERENCES savings;
    note VARCHAR[20000]
)

CREATE TABLE IF NOT EXISTS 'savings' (
    id SERIAL PRIMARY KEY,
    account_name TEXT REFERENCES accounts,
    interest INTEGER,
    currency currency,
    deposit BOOLEAN DEFAULT FALSE,
    capitalization BOOLEAN DEFAULT FALSE
)

CREATE TABLE IF NOT EXISTS 'items' (
    id SERIAL PRIMARY KEY,
    account_name TEXT REFERENCES accounts,
    title VARCHAR[40],
    amount INTEGER,
    currency currency,
    time_period period,
    icon TEXT
)