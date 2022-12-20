CREATE TABLE reservations_available_to_borrow
(
    id SERIAL,
    id_member VARCHAR NOT NULL,
    title_of_reserved_book VARCHAR NOT NULL,
    isbn_of_reserved_book VARCHAR NOT NULL,
    barcode_of_reserved_book VARCHAR NOT NULL,
    date_book_available_to_borrow VARCHAR NOT NULL,
    deadline_date_to_borrow_book DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_member) REFERENCES library_member (card_number),
    FOREIGN KEY (isbn_of_reserved_book) REFERENCES books (isbn),
    FOREIGN KEY (barcode_of_reserved_book) REFERENCES copies_of_books (barcode)
);