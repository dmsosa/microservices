CREATE TABLE IF NOT EXISTS items (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    stats_id INTEGER NOT NULL,
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