CREATE TYPE currencies AS ENUM ('USD', 'EUR', 'GBP')

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    stats_id INTEGER NOT NULL,
    stats_date TIMESTAMP,
    title VARCHAR(40),
    amount INTEGER,
    category ENUM ('FIXED', 'OCCASIONAL'),
    currency ENUM ('USD', 'EUR', 'GBP'),
    frequency ENUM ('DAY', 'WEEK', 'MONTH'),
    type ENUM ('INCOME', 'EXPENSE', 'SAVING'),
    PRIMARY KEY (id),
    FOREIGN KEY (stats_id) REFERENCES stats (id)
);