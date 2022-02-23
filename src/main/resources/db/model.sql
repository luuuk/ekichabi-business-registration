-- This file describes the schema of ekichabi's database.
-- Please keep this database up-to-date whenever you create a migration

CREATE TABLE business
(
    id            BIGSERIAL NOT NULL
        CONSTRAINT business_pk
            PRIMARY KEY,
    name          TEXT,
    category_id   INT REFERENCES category,
    subvillage_id INT REFERENCES subvillage
);

CREATE TABLE category
(
    id   SERIAL NOT NULL
        CONSTRAINT category_pk
            PRIMARY KEY,
    name TEXT
);

CREATE TABLE subcategory
(
    id          SERIAL
        CONSTRAINT subcategory_pk PRIMARY KEY,
    name        TEXT,
    category_id INT NOT NULL
        CONSTRAINT subcategory_category_fk REFERENCES category
);

CREATE TABLE business_to_subcategory
(
    business_id    BIGINT REFERENCES business (id),
    subcategory_id INT REFERENCES subcategory (id),

    CONSTRAINT business_to_subcategory_pk
        PRIMARY KEY (business_id, subcategory_id)
);

CREATE TABLE district
(
    id   SERIAL NOT NULL
        CONSTRAINT district_pk PRIMARY KEY,
    name TEXT
);

CREATE TABLE village
(
    id          SERIAL NOT NULL
        CONSTRAINT village_pk
            PRIMARY KEY,
    name        TEXT,
    district_id INT    NOT NULL
        CONSTRAINT village_district_fk
            REFERENCES district (id)
);

CREATE TABLE subvillage
(
    id         SERIAL NOT NULL
        CONSTRAINT subvillage_pk
            PRIMARY KEY,
    name       TEXT,
    village_id INT    NOT NULL
        CONSTRAINT subvillage_village_fk
            REFERENCES village (id)
);

CREATE TABLE phone_numbers
(
    id   SERIAL NOT NULL
        CONSTRAINT phone_number_pk
            PRIMARY KEY,
    number TEXT
);

ALTER TABLE business
    ADD COLUMN phone_number_id INT REFERENCES phone_numbers (id);