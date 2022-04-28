--1. Найти всех получателей (receiver_name, account_number), которым было отправлено более 10 платежей
SELECT a.account_name as receiver_name, a.account_number
FROM accounts a, payments p
WHERE p.receiver_account_id = a.id
GROUP BY a.account_name, a.account_number, p.receiver_account_id
HAVING COUNT(*) > 10;

--2. Найти всех клиентов(client.name), которым ни разу не переводили средства (INNER JOIN)
SELECT c.name
FROM clients c
WHERE c.id NOT IN (
    SELECT c.id
    FROM clients c
        JOIN accounts a on c.id = a.client_id
        JOIN payments p on a.id = p.receiver_account_id);

--2.1. Найти всех клиентов(client.name), которым ни разу не переводили средства (SUB-QUERY)
SELECT c.name
FROM clients c
WHERE c.id NOT IN (
    SELECT a.client_id
    FROM accounts a
    WHERE a.id IN (
        SELECT p.receiver_account_id
        FROM payments p
    )
);

--3. Найти все платежи, где счет плательщика и получателя имеет тип "Расчетный счет"
SELECT *
FROM payments p
         JOIN accounts a on p.payer_account_id = a.id
    OR p.receiver_account_id = a.id
         JOIN account_types t on a.account_type_id = t.id
    AND t.name = 'Расчетный счет';

--4. Вывести сумму всех платежей для счета получателя, номер которого оканчивается на 111 (INNER JOIN)
SELECT SUM(p.amount)
FROM payments p
         JOIN accounts a on p.receiver_account_id = a.id
    AND a.account_number LIKE '%111';

--4.1. Вывести сумму всех платежей для счета получателя, номер которого оканчивается на 111 (SUB-QUERY)
SELECT SUM(amount)
FROM payments p
WHERE p.receiver_account_id = (
    SELECT a.id
    FROM accounts a
    WHERE a.account_number LIKE '%111');

--5. Вывести уникальный список клиентов (client.name), которые выступают в качестве плательщика.
SELECT distinct c.name
FROM clients c
         JOIN accounts a on c.id = a.client_id
         JOIN payments p on a.id = p.payer_account_id;

--6. Вывести сумму всех платежей для счета плательщика с номером ${accountNumber} (INNER JOIN)
SELECT SUM(p.amount)
FROM payments p
         JOIN accounts a on p.payer_account_id = a.id
    AND a.account_number = '10020001233233234';

--6.1. Вывести сумму всех платежей для счета плательщика с номером ${accountNumber} (SUB-QUERY)
SELECT SUM(p.amount)
FROM payments p
WHERE p.payer_account_id = (
    SELECT a.id
    FROM accounts a
    WHERE a.account_number = '10020001233233234');

--7. Найти все счета плательщика (account_number), по которым сумма платежей превышает ${amount} рублей
SELECT a.account_name
FROM accounts a
        JOIN payments p on a.id = p.payer_account_id
GROUP BY a.id
HAVING SUM(p.amount) > 7000;

--8. Найти все рублевые платежи для клиентов (payment.id, client.name, account_number), имя которых начинается c буквы ${startLetter}
SELECT p.id, c.name, a.account_number
FROM payments p
    JOIN accounts a on p.payer_account_id = a.id
    JOIN currencies cur on a.currency_id = cur.id
                           AND cur.name = 'RUB'
    JOIN clients c on a.client_id = c.id
                      AND c.name LIKE 'B%';

--9. Найти все платежи(payment.id, payment.name, payment.amount), где счет плательщика имеет тип ${accountType}
SELECT p.id, p.name, p.amount
FROM payments p
    JOIN accounts a on p.payer_account_id = a.id
    JOIN account_types t on a.account_type_id = t.id
                            AND t.name = 'Расчетный счет';

--10. Найти всех клиентов(client.id, client.name), счета которых находятся в банке ${bankId}
SELECT c.id, c.name
FROM clients c
    JOIN accounts a on c.id = a.client_id
    JOIN banks b on a.bank_id = b.id
                    AND b.name = 'Probank';