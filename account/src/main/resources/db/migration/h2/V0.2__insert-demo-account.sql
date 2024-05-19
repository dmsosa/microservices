INSERT INTO accounts (name, last_seen, note, icon) VALUES (
    'demo',
    '2024-03-04',
    'demo notes!',
    'piggy'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo salary',
    '',
    1220,
    'FIXED',
    'USD',
    'MONTH',
    'INCOME'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo grocery',
    '',
    25,
    'OCCASIONAL',
    'USD',
    'DAY',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo gym',
    '',
    55,
    'FIXED',
    'USD',
    'WEEK',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo payment',
    '',
    420,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo food',
    '',
    250,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo insurance',
    '',
    350,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);
