DELETE FROM payments;
DELETE FROM accounts;
DELETE FROM account_types;
DELETE FROM banks;
DELETE FROM clients;
DELETE FROM currencies;

INSERT INTO currencies VALUES (1, 'RUB');
INSERT INTO currencies VALUES (2, 'DOLLAR');
INSERT INTO currencies VALUES (3, 'EURO');

INSERT INTO clients VALUES (1, 'Bob');
INSERT INTO clients VALUES (2, 'John');
INSERT INTO clients VALUES (3, 'Maria');
INSERT INTO clients VALUES (4, 'Sofia');
INSERT INTO clients VALUES (5, 'Junior');

INSERT INTO banks VALUES (1, 'Nubank', '044525974');
INSERT INTO banks VALUES (2, 'Vabank', '044525975');
INSERT INTO banks VALUES (3, 'Probank', '044525976');

INSERT INTO account_types VALUES (1, 'Текущий счет');
INSERT INTO account_types VALUES (2, 'Расчетный счет');
INSERT INTO account_types VALUES (3, 'Кредитный(судный счет)');

INSERT INTO accounts VALUES (1, '10020001233233111', 1, 1, 1, 'Bob-RUB-Nubank-Текущий', 1);
INSERT INTO accounts VALUES (2, '10020001233233232', 2, 2, 2, 'John-DOLLAR-Vabank-Расчетный', 2);
INSERT INTO accounts VALUES (3, '10020001233233233', 3, 3, 3, 'Maria-EURO-Probank-Кредитный(судный)', 3);
INSERT INTO accounts VALUES (4, '10020001233233234', 3, 4, 3, 'Sofia-EURO-Probank-Кредитный(судный)', 3);
INSERT INTO accounts VALUES (5, '10020001233233235', 2, 5, 2, 'Junior-DOLLAR-Vabank-Расчетный', 2);

INSERT INTO payments VALUES(1, 1, 2, 1000.0, '1 платеж');
INSERT INTO payments VALUES(2, 1, 2, 1000.0, '2 платеж');
INSERT INTO payments VALUES(3, 1, 3, 1000.0, '3 платеж');
INSERT INTO payments VALUES(4, 1, 2, 1000.0, '4 платеж');
INSERT INTO payments VALUES(5, 1, 3, 1000.0, '5 платеж');
INSERT INTO payments VALUES(6, 1, 2, 1000.0, '6 платеж');
INSERT INTO payments VALUES(7, 1, 2, 1000.0, '7 платеж');
INSERT INTO payments VALUES(8, 1, 3, 1000.0, '8 платеж');
INSERT INTO payments VALUES(9, 1, 2, 1000.0, '9 платеж');
INSERT INTO payments VALUES(10, 2, 1, 1000.0, '10 платеж');
INSERT INTO payments VALUES(11, 2, 1, 1000.0, '11 платеж');
INSERT INTO payments VALUES(12, 3, 1, 1000.0, '12 платеж');
INSERT INTO payments VALUES(13, 3, 1, 1000.0, '13 платеж');
INSERT INTO payments VALUES(14, 4, 1, 1000.0, '14 платеж');
INSERT INTO payments VALUES(15, 2, 1, 1000.0, '15 платеж');
INSERT INTO payments VALUES(16, 3, 1, 1000.0, '16 платеж');
INSERT INTO payments VALUES(17, 2, 1, 1000.0, '17 платеж');
INSERT INTO payments VALUES(18, 4, 1, 1000.0, '18 платеж');
INSERT INTO payments VALUES(19, 3, 1, 1000.0, '19 платеж');
INSERT INTO payments VALUES(20, 2, 1, 1000.0, '20 платеж');
INSERT INTO payments VALUES(21, 2, 5, 500.0, '21 платеж');