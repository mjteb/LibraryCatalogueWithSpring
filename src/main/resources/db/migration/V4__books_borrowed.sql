CREATE TABLE books_borrowed
(
    id SERIAL,
    id_book VARCHAR NOT NULL,
    id_member VARCHAR NOT NULL,
    date_book_borrowed DATE NOT NULL,
    due_date DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_book) REFERENCES copies_of_books (barcode),
    FOREIGN KEY (id_member) REFERENCES library_member (card_number)
);