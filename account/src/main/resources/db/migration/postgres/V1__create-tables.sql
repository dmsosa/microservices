CREATE TYPE frequencies AS enum ('DAY', 'WEEK', 'MONTH');
CREATE TYPE categories AS enum ('FIXED', 'OCCASIONAL');
CREATE TYPE currencies as ENUM ('EUR', 'GBP', 'USD');
CREATE TYPE item_types as ENUM('INCOME', 'EXPENSE', 'SAVING');

CREATE TABLE IF NOT EXISTS accounts (
    name VARCHAR(40) NOT NULL UNIQUE PRIMARY KEY,
    last_seen TIMESTAMP,
    note VARCHAR(20000)
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    account_name VARCHAR(40) NOT NULL,
    title VARCHAR(40),
    icon TEXT,
    amount INTEGER,
    category categories,
    currency currencies,
    frequency frequencies,
    type item_types,
    FOREIGN KEY (account_name) REFERENCES accounts (name)
);