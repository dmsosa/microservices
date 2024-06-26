CREATE TYPE currencies AS ENUM ('USD', 'EUR', 'GBP')

CREATE TABLE IF NOT EXISTS stats (
    id SERIAL PRIMARY KEY,
    currency currencies,
    account_name VARCHAR(40) NOT NULL,
    stats_date TIMESTAMP,
    total_incomes INTEGER,
    total_expenses INTEGER,
    total_savings INTEGER,
    );

