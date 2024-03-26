CREATE TABLE IF NOT EXISTS notisettings (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    account_name VARCHAR(40) NOT NULL,
    active BOOLEAN,
    last_notified DATE,
    frequency ENUM('MINUTE', 'DAY', 'WEEK', 'MONTH', 'QUARTER', 'YEAR'),
    type ENUM('BACKUP', 'REMIND'),
    PRIMARY KEY (id),
    CONSTRAINT ACCOUNT_NOTI UNIQUE(account_name, type)
);

CREATE TABLE IF NOT EXISTS recipients (
    account_name VARCHAR(40) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    PRIMARY KEY (account_name)
);