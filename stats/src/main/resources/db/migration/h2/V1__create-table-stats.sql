CREATE TABLE IF NOT EXISTS stats (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    currency ENUM ('USD', 'EUR', 'GBP'),
    account_name VARCHAR(40) NOT NULL,
    stats_date TIMESTAMP,
    total_incomes NUMERIC,
    total_expenses NUMERIC,
    total_savings NUMERIC,
    PRIMARY KEY (id)
    );

