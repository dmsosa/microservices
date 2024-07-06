INSERT INTO accounts (name, last_seen, note, avatar, currency) VALUES (
    'test',
    '2024-03-04',
    'test notes!',
    'piggy',
    DEFAULT
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'test salary',
    'work',
    1220,
    'FIXED',
    'USD',
    'MONTH',
    'INCOME'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'test grocery',
    'food',
    25,
    'OCCASIONAL',
    'USD',
    'DAY',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'test gym',
    'sport',
    55,
    'FIXED',
    'USD',
    'WEEK',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'test payment',
    'house',
    420,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'test food',
    'food',
    250,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);

INSERT INTO items (account_id, title, icon, amount, category, currency, frequency, type) VALUES (
    1,
    'test insurance',
    'health',
    350,
    'FIXED',
    'USD',
    'MONTH',
    'EXPENSE'
);
