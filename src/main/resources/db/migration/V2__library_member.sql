CREATE TABLE library_member
(
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    card_number VARCHAR NOT NULL,
    date_of_birth DATE NOT NULL,
    membership_expiration_date DATE NOT NULL,
    outstanding_late_fees DECIMAL(5,2),
    PRIMARY KEY (card_number)
);

