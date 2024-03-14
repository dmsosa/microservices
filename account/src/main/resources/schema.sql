CREATE TABLE IF NOT EXISTS accounts (
    name VARCHAR(40) UNIQUE NOT NULL,
    last_seen DATE,
    note VARCHAR(3000),
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS items (
    id INTEGER NOT NULL AUTO_INCREMENT,
    account_name VARCHAR(40),
    title VARCHAR(40),
    icon TEXT,
    amount NUMERIC,
    currency ENUM('USD', 'EUR', 'GBP'),
    frequency ENUM('DAY', 'MONTH', 'QUARTER', 'YEAR'),
    type ENUM('SAVING', 'INCOME', 'EXPENSE'),
    PRIMARY KEY (id),
    FOREIGN KEY (account_name) REFERENCES accounts (name)
);
