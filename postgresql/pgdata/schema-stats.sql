CREATE TYPE currency as ENUM ('EUR', 'GBP', 'USD');
CREATE TYPE stats_metrics as ENUM ('INCOMES_AMOUNT', 'EXPENSES_AMOUNT', 'SAVINGS_AMOUNT');
CREATE TYPE item_type as ENUM('INCOME', 'EXPENSE');
CREATE TABLE IF NOT EXISTS 'datapoints' (
    account_name TEXT NOT NULL,
    data_date DATE NOT NULL,
    PRIMARY KEY (account_name, data_date)
);

CREATE TABLE IF NOT EXISTS 'items' (
    account_name TEXT NOT NULL,
    data_date DATE NOT NULL,
    title TEXT,
    amount INTEGER,
    currency currency,
    type item_type,
    FOREIGN KEY (account_name, data_date) REFERENCES datapoints(account_name, data_date)
);

CREATE TABLE IF NOT EXISTS 'statistics' (
    account_name TEXT NOT NULL,
    data_date DATE NOT NULL,
    statistics INTEGER,
    type stats_metrics,
    FOREIGN KEY (account_name, data_date) REFERENCES datapoints(account_name, data_date)
);

CREATE TABLE IF NOT EXISTS 'rates' (
    account_name TEXT NOT NULL,
    data_date DATE NOT NULL,
    currency currency,
    rate INTEGER,
    FOREIGN KEY (account_name, data_date) REFERENCES datapoints(account_name, data_date)
);