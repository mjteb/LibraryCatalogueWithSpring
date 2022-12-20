CREATE TABLE books_reserved
(
    id SERIAL,
    id_member VARCHAR NOT NULL,
    title_of_reserved_book VARCHAR NOT NULL,
    isbn_of_reserved_book VARCHAR NOT NULL,
    date_book_reserved DATE NOT NULL,
    position_in_line_for_book INTEGER,
    PRIMARY KEY (id),
    FOREIGN KEY (id_member) REFERENCES library_member (card_number),
    FOREIGN KEY (isbn_of_reserved_book) REFERENCES books (isbn)
);