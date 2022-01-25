CREATE TABLE business
(
    id  BIGSERIAL NOT NULL
        CONSTRAINT business_pk
        PRIMARY KEY,

    name TEXT
)