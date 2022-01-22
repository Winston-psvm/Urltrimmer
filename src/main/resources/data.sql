INSERT INTO users ( email, password)
VALUES ('user@yandex.ru', '{noop}password'),
       ('admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2);