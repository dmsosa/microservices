CREATE TABLE IF NOT EXISTS datapoints (
    account_name VARCHAR(40) NOT NULL,
    data_date DATE,
    PRIMARY KEY (account_name, data_date)
);

CREATE TABLE IF NOT EXISTS items (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    account_name VARCHAR(40) NOT NULL,
    data_date DATE,
    title VARCHAR(40),
    amount NUMERIC,
    category ENUM ('FIXED', 'OCCASIONAL'),
    currency ENUM ('EUR', 'USD', 'GBP'),
    frequency ENUM ('DAY', 'MONTH', 'QUARTER', 'YEAR'),
    type ENUM ('INCOME', 'EXPENSE', 'SAVING'),
    PRIMARY KEY (id),
    FOREIGN KEY (account_name, data_date) REFERENCES datapoints (account_name, data_date)
);

CREATE TABLE IF NOT EXISTS stats (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    account_name VARCHAR(40) NOT NULL,
    data_date DATE,
    total_incomes NUMERIC,
    total_expenses NUMERIC,
    total_savings NUMERIC,
    PRIMARY KEY (id),
    FOREIGN KEY (account_name, data_date) REFERENCES datapoints (account_name, data_date)
);