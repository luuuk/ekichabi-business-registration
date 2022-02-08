CREATE TABLE account
(
    id BIGSERIAL NOT NULL
        CONSTRAINT account_pk PRIMARY KEY,

    name TEXT NOT NULL UNIQUE,

    password TEXT

);

ALTER TABLE business
ADD COLUMN account_id BIGINT REFERENCES account(id);