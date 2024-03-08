CREATE TYPE frequencies AS enum ('DAY', 'WEEK', 'MONTH', 'QUARTER', 'YEAR');
CREATE TYPE categories AS enum ('FIXED', 'OCCASIONAL');
CREATE TYPE currencies as ENUM ('EUR', 'GBP', 'USD');
CREATE TYPE item_types as ENUM('INCOME', 'EXPENSE', 'SAVING');

CREATE TABLE IF NOT EXISTS datapoints (
    account_name VARCHAR(40) NOT NULL,
    data_date DATE NOT NULL,
    PRIMARY KEY (account_name, data_date)
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    account_name VARCHAR(40) NOT NULL,
    data_date DATE NOT NULL,
    title VARCHAR(40),
    amount INTEGER,
    category categories,
    currency currencies,
    frequency frequencies,
    type item_types,
    FOREIGN KEY (account_name, data_date) REFERENCES datapoints(account_name, data_date)
);

CREATE TABLE IF NOT EXISTS statistics (
    id SERIAL PRIMARY KEY,
    account_name VARCHAR(40) NOT NULL,
    data_date DATE NOT NULL,
    total_incomes INTEGER,
    total_expenses INTEGER,
    total_savings INTEGER,
    FOREIGN KEY (account_name, data_date) REFERENCES datapoints(account_name, data_date)
);