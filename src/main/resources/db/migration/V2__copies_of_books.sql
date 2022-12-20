CREATE TABLE copies_of_books
(
    isbn_of_title VARCHAR NOT NULL,
    title VARCHAR NOT NULL,
    barcode VARCHAR NOT NULL,
    due_date DATE,
    status VARCHAR,
    PRIMARY KEY (barcode),
    FOREIGN KEY (isbn_of_title) REFERENCES books (isbn)
);

