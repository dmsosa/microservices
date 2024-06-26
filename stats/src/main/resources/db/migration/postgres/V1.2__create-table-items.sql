CREATE TYPE categories AS ENUM ('FIXED', 'OCCASIONAL')
CREATE TYPE frequencies AS ENUM ('DAY', 'WEEK', 'MONTH')
CREATE TYPE types AS ENUM ('INCOME', 'EXPENSE', 'SAVING')

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    stats_id INTEGER NOT NULL,
    stats_date TIMESTAMP,
    title VARCHAR(40),
    amount INTEGER,
    category categories,
    currency currencies,
    frequency frequencies,
    type types,
    PRIMARY KEY (id),
    FOREIGN KEY (stats_id) REFERENCES stats (id)
);