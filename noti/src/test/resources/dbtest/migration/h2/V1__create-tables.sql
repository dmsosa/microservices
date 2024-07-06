CREATE TABLE IF NOT EXISTS notifications (
    id INTEGER NOT NULL UNIQUE AUTO_INCREMENT,
    account_name VARCHAR(40) NOT NULL,
    email VARCHAR(40) NOT NULL,
    frequency INTEGER NOT NULL,
    noti_type ENUM('BACKUP', 'REMIND'),
    active BOOLEAN,
    last_notified TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT ACCOUNT_NOTI UNIQUE(account_name, noti_type)
);