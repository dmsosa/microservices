CREATE TABLE IF NOT EXISTS stats (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    account_name VARCHAR(40) NOT NULL,
    stats_date TIMESTAMP,
    total_incomes NUMERIC,
    total_expenses NUMERIC,
    PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS items (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    stats_id VARCHAR(40) NOT NULL,
    stats_date TIMESTAMP,
    title VARCHAR(40),
    amount NUMERIC,
    category ENUM ('FIXED', 'OCCASIONAL'),
    currency ENUM ('USD', 'EUR', 'GBP'),
    frequency ENUM ('DAY', 'WEEK', 'MONTH'),
    type ENUM ('INCOME', 'EXPENSE', 'SAVING'),
    PRIMARY KEY (id),
    FOREIGN KEY (stats_id) REFERENCES stats (id)
);