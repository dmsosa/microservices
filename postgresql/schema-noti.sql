CREATE TYPE frequencies AS enum ('DAY', 'MONTH', 'QUARTER', 'YEAR');
CREATE TYPE noti_types as ENUM ('BACKUP', 'REMIND');

CREATE TABLE IF NOT EXISTS notisettings (
    id SERIAL PRIMARY KEY,
    account_name TEXT NOT NULL,
    active BOOLEAN NOT NULL,
    last_notified DATE,
    frequency frequencies,
    type noti_types
);

CREATE TABLE IF NOT EXISTS recipients (
    account_name TEXT NOT NULL UNIQUE PRIMARY KEY,
    email TEXT NOT NULL UNIQUE
);
