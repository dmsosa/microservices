INSERT INTO accounts (name, last_seen, notes) VALUES (
    'demo',
    '2024-03-04',
    'demo notes!'
);

INSERT INTO items (account_name, title, amount, currency, frequency, type) VALUES (
    'demo',
    'demo salary',
    1220,
    'USD',
    'MONTH',
    'INCOME'
);

INSERT INTO items (account_name, title, amount, currency, frequency, type) VALUES (
    'demo',
    'demo grocery',
    25,
    'USD',
    'DAY',
    'EXPENSE'
);

INSERT INTO items (account_name, title, amount, currency, frequency, type) VALUES (
    'demo',
    'demo gym',
    55,
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_name, title, amount, currency, frequency, type) VALUES (
    'demo',
    'demo payment',
    420,
    'USD',
    'QUARTER',
    'EXPENSE'
);

INSERT INTO items (account_name, title, amount, currency, frequency, type) VALUES (
    'demo',
    'demo food',
    250,
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_name, title, amount, currency, frequency, type) VALUES (
    'demo',
    'demo insurance',
    350,
    'USD',
    'YEAR',
    'EXPENSE'
);
