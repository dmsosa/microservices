CREATE TYPE frequency as ENUM ('WEEKLY', 'MONTHLY', 'QUARTERLY', 'YEARLY');

CREATE TYPE noti_type as ENUM ('BACKUP', 'REMIND');

CREATE TABLE IF NOT EXISTS 'notisettings' (
    id SERIAL PRIMARY KEY,
    account_name TEXT NOT NULL,
    active BOOLEAN NOT NULL,
    last_notified DATE,
    frequency frequency,
    type noti_type
)

CREATE TABLE IF NOT EXISTS 'recipients' (
    account_name TEXT NOT NULL UNIQUE PRIMARY KEY,
    email TEXT NOT NULL UNIQUE
)
