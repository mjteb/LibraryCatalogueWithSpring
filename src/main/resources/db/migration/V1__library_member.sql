CREATE TABLE library_member
(
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    card_number VARCHAR NOT NULL,
    phone_number VARCHAR NOT NULL,
    date_of_birth DATE NOT NULL,
    membership_expiration_date DATE,
    library_fees_from_books_currently_borrowed DECIMAL(5,2),
    library_fees_from_returned_books DECIMAL(5,2),
    total_library_fees DECIMAL(5,2),
    number_of_books_borrowed INTEGER,
    number_of_books_reserved INTEGER,
    PRIMARY KEY (card_number)
);

