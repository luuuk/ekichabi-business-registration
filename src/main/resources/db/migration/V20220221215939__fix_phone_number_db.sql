ALTER TABLE business
    DROP COLUMN phone_number_id;

CREATE TABLE IF NOT EXISTS business_entity_phone_numbers
(
    business_entity_id INT NOT NULL,
    phone_numbers TEXT
);

DROP TABLE phone_numbers;