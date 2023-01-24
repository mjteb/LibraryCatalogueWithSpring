INSERT INTO library_member (last_name, first_name, phone_number, date_of_birth, card_number,
membership_expiration_date, library_fees_from_books_currently_borrowed, library_fees_from_returned_books,
total_library_fees, number_of_books_borrowed, number_of_books_reserved)
VALUES ('Tebeka', 'Michele', '5147929595','1950-04-01', 'TEBEMIC19500401', '2023-04-01', 0.00, 0.00, 0.00, 0, 0),
('Tebeka', 'Claire', '5147929595','1950-04-01', 'TEBECLA19500401', '2023-04-01', 0.00, 0.00, 0.00, 0, 0),
('Picot', 'Ludwig', '5147929595','1950-04-01', 'PICOLUD19500401', '2023-04-01', 0.00, 0.00, 0.00, 0, 0),
('Smith', 'Jones', '5147929595','1950-04-01', 'SMITJON19500401', '2023-04-01', 0.00, 0.00, 0.00, 0, 0),
('Smith', 'Kayla', '5147929595','1950-04-01', 'SMITKAY19500401', '2023-04-01', 0.00, 0.00, 0.00, 0, 0);


INSERT INTO books (title, author, isbn, total_number_of_copies, number_of_copies_available, number_of_reservations)
VALUES ('Normal People', 'Sally Rooney', '9781984822178', 2, 2, 0),
('Conversations with Friends', 'Sally Rooney', '9781984824561', 1, 1, 0),
('Where the Crawdads Sing', 'Delia Owens', '9780735219113', 2, 2, 0),
('It Ends with Us', 'Colleen Hoover ', '9781501110368', 1, 1, 0);

INSERT INTO copies_of_books (isbn_of_title, title, barcode, status)
VALUES ('9781984822178', 'Normal People', 'af7ee5d2-d278-4e8d-bc05-c5481af3d837', 'AVAILABLE'),
('9781984822178', 'Normal People', 'c9a6e403-4e23-4931-a957-2c6bf3fe3ce5', 'AVAILABLE'),
('9781984824561', 'Conversations with Friends', '1b0ce2bb-7f41-47c8-9a4b-3b4279459ded', 'AVAILABLE'),
('9780735219113', 'Where the Crawdads Sing', '8594343b-144b-427e-be37-bb5aedf98d57', 'AVAILABLE'),
('9780735219113', 'Where the Crawdads Sing', '2fc93c69-d630-4180-a1f4-7de3cd844425', 'AVAILABLE'),
('9781501110368', 'It Ends with Us', 'fcb17898-1d6b-43b4-adca-b83b173e0cce', 'AVAILABLE');

INSERT INTO roles (name)
VALUES ('user'),
('employee'),
('admin');


