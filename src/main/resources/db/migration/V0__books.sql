CREATE TABLE books
(
    title VARCHAR NOT NULL,
    author VARCHAR NOT NULL,
    isbn VARCHAR NOT NULL,
    total_number_of_copies INTEGER,
    number_of_copies_available INTEGER,
    PRIMARY KEY (isbn)
);

