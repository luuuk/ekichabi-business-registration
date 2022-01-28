-- This file describes the schema of ekichabi's database.
-- Please keep this database up-to-date whenever you create a migration

CREATE TABLE business
(
    id         BIGSERIAL NOT NULL
        CONSTRAINT business_pk
            PRIMARY KEY,
    name       TEXT,
    category   INT REFERENCES category,
    district   INT REFERENCES district,
    village    INT REFERENCES village,
    subvillage INT REFERENCES subvillage
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
    id       SERIAL
        CONSTRAINT subcategory_pk PRIMARY KEY,
    name     TEXT,
    category INT NOT NULL
        CONSTRAINT subcategory_category_fk REFERENCES category
);

CREATE TABLE business_to_subcategory
(
    business    BIGINT REFERENCES business (id),
    subcategory INT REFERENCES subcategory (id),

    CONSTRAINT business_to_subcategory_pk
        PRIMARY KEY (business, subcategory)
);

CREATE TABLE district
(
    id   SERIAL NOT NULL
        CONSTRAINT district_pk PRIMARY KEY,
    name TEXT
);

CREATE TABLE village
(
    id       SERIAL NOT NULL
        CONSTRAINT village_pk
            PRIMARY KEY,
    name     TEXT,
    district INT    NOT NULL
        CONSTRAINT village_district_fk
            REFERENCES district (id)
);

CREATE TABLE subvillage
(
    id      SERIAL NOT NULL
        CONSTRAINT subvillage_pk
            PRIMARY KEY,
    name    TEXT,
    village INT    NOT NULL
        CONSTRAINT subvillage_village_fk
            REFERENCES village (id)
);
