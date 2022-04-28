CREATE TABLE IF NOT EXISTS public.clients(
    id bigint PRIMARY KEY NOT NULL,
    name varchar(100)
);

CREATE TABLE IF NOT EXISTS public.currencies(
    id bigint PRIMARY KEY NOT NULL,
    name varchar(100)
);

CREATE TABLE IF NOT EXISTS public.banks(
    id bigint PRIMARY KEY NOT NULL,
    name varchar(100),
    bic varchar(100)
);

CREATE TABLE IF NOT EXISTS public.account_types(
    id bigint PRIMARY KEY NOT NULL,
    name varchar(100)
);

CREATE TABLE IF NOT EXISTS public.accounts(
    id bigint PRIMARY KEY NOT NULL,
    account_number varchar(100),
    bank_id bigint NOT NULL,
    FOREIGN KEY (bank_id) REFERENCES public.banks(id),
    client_id bigint NOT NULL,
    FOREIGN KEY (client_id) REFERENCES public.clients(id),
    currency_id bigint NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES public.currencies(id),
    account_name varchar(255),
    account_type_id bigint NOT NULL,
    FOREIGN KEY (account_type_id) REFERENCES public.account_types(id)
);

CREATE TABLE IF NOT EXISTS public.payments(
    id bigint PRIMARY KEY NOT NULL,
    payer_account_id bigint NOT NULL,
    FOREIGN KEY (payer_account_id) REFERENCES public.accounts(id),
    receiver_account_id bigint NOT NULL,
    FOREIGN KEY (receiver_account_id) REFERENCES public.accounts(id),
    amount float,
    name varchar(255)
);