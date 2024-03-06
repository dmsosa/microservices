CREATE TYPE currencies AS enum ('EUR', 'GBP', 'USD');
CREATE TYPE frequencies AS enum ('DAY', 'WEEK', 'MONTH', 'QUARTER', 'YEAR');
CREATE TYPE item_types AS enum ('INCOME', 'EXPENSE', 'SAVING');


CREATE TABLE IF NOT EXISTS accounts (
    name VARCHAR[40] UNIQUE NOT NULL,
    last_seen DATE,
    notes VARCHAR[3000]
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    account_name VARCHAR[40],
    title VARCHAR[40],
    amount NUMERIC,
    currency currencies,
    frequency frequencies,
    type item_types,
    FOREIGN KEY (account_name) REFERENCES accounts (name)
);
