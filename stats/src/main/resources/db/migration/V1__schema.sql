CREATE TYPE currencies AS enum ('EUR', 'GBP', 'USD');
CREATE TYPE frequencies AS enum ('DAY', 'WEEK', 'MONTH', 'QUARTER', 'YEAR');
CREATE TYPE item_types AS enum ('INCOME', 'EXPENSE', 'SAVING');

CREATE TYPE item AS (
    title VARCHAR[40],
    amount NUMERIC,
    currency currencies,
    frequency frequencies,
    type item_types
);

CREATE TABLE IF NOT EXISTS datapoints (
    account_name VARCHAR[40],
    data_date DATE,
    PRIMARY KEY (account_name, data_date)
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    title VARCHAR[40],
    amount NUMERIC,
    currency currencies,
    frequency frequencies,
    type item_types
);
CREATE TABLE IF NOT EXISTS stats (
    id SERIAL PRIMARY KEY,
    incomes item[],
    expenses item[],
    savings item[],
    frequency frequencies,
    type item_types
);
