--Alle die Dateninhalt unseres Tabelle zu entfernen, um nur Testbenutzer zu haben, und die Benutzerrole zu verbessern
INSERT INTO users (id, username, email, password, role) VALUES (
'test', 'test', 'test@gmail.com', '123', 'admin') ON CONFLICT DO NOTHING/UPDATE;