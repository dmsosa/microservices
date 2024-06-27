CREATE TYPE frequencies AS enum ('DAY', 'WEEK', 'MONTH');
CREATE TYPE categories AS enum ('FIXED', 'OCCASIONAL');
CREATE TYPE currencies as ENUM ('EUR', 'GBP', 'USD');
CREATE TYPE item_types as ENUM('INCOME', 'EXPENSE', 'SAVING');

CREATE TABLE IF NOT EXISTS accounts (
    id SERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE,
    last_seen TIMESTAMP,
    note VARCHAR(20000),
    avatar TEXT,
    currency currencies DEFAULT 'USD'
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    title VARCHAR(40) NOT NULL,
    icon TEXT,
    amount INTEGER,
    category categories,
    currency currencies,
    frequency frequencies,
    type item_types,
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);