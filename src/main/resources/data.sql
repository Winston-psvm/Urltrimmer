ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users ( id, email, password)
VALUES (100000, 'user@yandex.ru', '{noop}password'),
       (100001, 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);