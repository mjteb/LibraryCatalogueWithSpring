CREATE TABLE user_roles
(
    user_id SERIAL,
    role_id SERIAL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);