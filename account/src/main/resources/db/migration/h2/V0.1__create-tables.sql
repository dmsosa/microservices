CREATE TABLE IF NOT EXISTS accounts (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) UNIQUE NOT NULL,
    last_seen TIMESTAMP,
    note VARCHAR(3000),
    avatar TEXT,
    currency ENUM('USD', 'EUR', 'GBP') DEFAULT 'USD',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items (
    id INTEGER NOT NULL AUTO_INCREMENT,
    account_id INTEGER NOT NULL,
    title VARCHAR(40) UNIQUE NOT NULL,
    icon TEXT,
    amount NUMERIC,
    category ENUM('FIXED', 'OCCASIONAL'),
    currency ENUM('USD', 'EUR', 'GBP'),
    frequency ENUM('DAY', 'WEEK', 'MONTH'),
    type ENUM('INCOME', 'EXPENSE', 'SAVING'),
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES accounts (id)
);
