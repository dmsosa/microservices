CREATE TYPE notiTypes AS ENUM ('BACKUP', 'REMIND');

CREATE TABLE notifications (
    id SERIAL PRIMARY KEY,
    account_name VARCHAR(40) NOT NULL,
    email VARCHAR (40) NOT NULL,
    frequency INTEGER NOT NULL,
    noti_type notiTypes,
    active BOOLEAN,
    last_notified TIMESTAMP,
    UNIQUE (account_name, email, noti_type)
);