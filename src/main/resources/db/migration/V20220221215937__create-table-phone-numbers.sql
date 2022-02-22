CREATE TABLE phone_numbers
(
    id   SERIAL NOT NULL
        CONSTRAINT phone_number_pk
            PRIMARY KEY,
    number TEXT
);

ALTER TABLE business
    ADD COLUMN phone_number_id INT REFERENCES phone_numbers (id);