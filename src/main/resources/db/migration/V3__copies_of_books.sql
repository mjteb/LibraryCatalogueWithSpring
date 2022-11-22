CREATE TABLE copies_of_books
(
    isbn_of_title VARCHAR NOT NULL,
    barcode VARCHAR NOT NULL,
    due_date DATE,
    member_borrowing_document VARCHAR,
    PRIMARY KEY (barcode),
    FOREIGN KEY (isbn_of_title) REFERENCES books (isbn),
    FOREIGN KEY (member_borrowing_document) REFERENCES library_member (card_number)
);

