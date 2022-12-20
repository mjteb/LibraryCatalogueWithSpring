CREATE TABLE users
(
    id SERIAL,
    username VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    card_number VARCHAR NOT NULL,
    PRIMARY KEY (id)
);