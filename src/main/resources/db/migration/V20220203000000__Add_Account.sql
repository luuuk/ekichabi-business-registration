CREATE TABLE account
(
    id       BIGSERIAL NOT NULL
        CONSTRAINT account_pk PRIMARY KEY,

    name     TEXT      NOT NULL UNIQUE,

    password TEXT

);

create TABLE business_account
(
    business_id BIGINT REFERENCES business (id),
    account_id  BIGINT REFERENCES account (id)
);