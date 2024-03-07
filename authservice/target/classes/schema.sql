CREATE TABLE IF NOT EXISTS customers (
    username VARCHAR(40) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role CHARACTER,
    PRIMARY KEY (username)
);