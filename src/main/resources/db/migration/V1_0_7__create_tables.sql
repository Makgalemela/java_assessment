-- public.account definition

-- Drop table

-- DROP TABLE public.account;

CREATE TABLE public.account (
                                account_number int8 NOT NULL,
                                created_on timestamp NOT NULL,
                                updated_on timestamp NOT NULL,
                                first_name varchar(255) NULL,
                                last_name varchar(255) NULL,
                                account_status varchar(255) NULL,
                                CONSTRAINT account_pkey PRIMARY KEY (account_number)
);


-- public.feature_dated_transactions definition

-- Drop table

-- DROP TABLE public.feature_dated_transactions;

CREATE TABLE public.feature_dated_transactions (
                                                   id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE),
                                                   created_on timestamp NOT NULL,
                                                   updated_on timestamp NOT NULL,
                                                   amount numeric(19, 2) NOT NULL,
                                                   dated_for date NOT NULL,
                                                   reference varchar(255) NOT NULL,
                                                   status varchar(255) NOT NULL,
                                                   transaction_type varchar(255) NOT NULL,
                                                   account_number int8 NOT NULL,
                                                   CONSTRAINT feature_dated_transactions_pkey PRIMARY KEY (id)
);


-- public.feature_dated_transactions foreign keys

ALTER TABLE public.feature_dated_transactions ADD CONSTRAINT fkpexf0ih1b8e9198bhsgr3uukr FOREIGN KEY (account_number) REFERENCES public.account(account_number);



-- public.removed_transactions definition

-- Drop table

-- DROP TABLE public.removed_transactions;

CREATE TABLE public.removed_transactions (
                                             id int8 NOT NULL,
                                             created_on timestamp NOT NULL,
                                             amount numeric(19, 2) NOT NULL,
                                             credit timestamp NOT NULL,
                                             reference varchar(255) NOT NULL,
                                             transaction_type varchar(255) NOT NULL,
                                             account_number int8 NOT NULL,
                                             CONSTRAINT removed_transactions_pkey PRIMARY KEY (id)
);


-- public.removed_transactions foreign keys

ALTER TABLE public.removed_transactions ADD CONSTRAINT fkbefx78k1xo414jqe3d26rc0t1 FOREIGN KEY (account_number) REFERENCES public.account(account_number);



-- public.transctions definition

-- Drop table

-- DROP TABLE public.transctions;

CREATE TABLE public.transctions (
                                    id int8 NOT NULL,
                                    created_on timestamp NOT NULL,
                                    amount numeric(19, 2) NOT NULL,
                                    reference varchar(255) NOT NULL,
                                    transaction_type varchar(255) NOT NULL,
                                    account_number int8 NOT NULL,
                                    CONSTRAINT transctions_pkey PRIMARY KEY (id)
);


-- public.transctions foreign keys

ALTER TABLE public.transctions ADD CONSTRAINT fkac62do9ka4dadkxh0sqgashqk FOREIGN KEY (account_number) REFERENCES public.account(account_number);
