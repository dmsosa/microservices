INSERT INTO accounts (name, last_seen, avatar, icon) VALUES (
    'demo',
    '2024-03-04',
    'demo notes!',
    'fox'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo salary',
    'work',
    1220,
    'FIXED',
    'USD',
    'MONTH',
    'INCOME'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo grocery',
    'food',
    25,
    'OCCASIONAL',
    'USD',
    'DAY',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo gym',
    'sport',
    55,
    'FIXED',
    'USD',
    'WEEK',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo payment',
    'house',
    420,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo food',
    'food',
    250,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'demo insurance',
    'health',
    350,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);
