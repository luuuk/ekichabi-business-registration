CREATE TABLE category
(
    id  SERIAL NOT NULL
        CONSTRAINT category_pk
        PRIMARY KEY,
    name TEXT
);

CREATE TABLE subcategory
(
    id  SERIAL
        CONSTRAINT subcategory_pk
        PRIMARY KEY,
    name TEXT,
    category INT NOT NULL
        CONSTRAINT subcategory_category_fk
        REFERENCES category
);

CREATE TABLE business_to_subcategory
(
    business BIGINT REFERENCES business (id),
    subcategory INT REFERENCES subcategory (id),

    CONSTRAINT business_to_subcategory_pk
    PRIMARY KEY (business, subcategory)
);

CREATE TABLE district
(
    id  SERIAL NOT NULL
        CONSTRAINT district_pk
        PRIMARY KEY,
    name TEXT
);

CREATE TABLE village
(
    id  SERIAL NOT NULL
        CONSTRAINT village_pk
        PRIMARY KEY,
    name TEXT,
    district INT NOT NULL
             CONSTRAINT village_district_fk
             REFERENCES district (id)
);

CREATE TABLE subvillage
(
    id  SERIAL NOT NULL
        CONSTRAINT subvillage_pk
        PRIMARY KEY,
    name TEXT,
    village INT NOT NULL
            CONSTRAINT subvillage_village_fk
            REFERENCES village (id)
);

ALTER TABLE business
ADD COLUMN category INT REFERENCES category,
ADD COLUMN district INT REFERENCES district,
ADD COLUMN village INT REFERENCES village,
ADD COLUMN subvillage INT REFERENCES subvillage;