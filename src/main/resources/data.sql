INSERT INTO users ( email, password)
VALUES ('user@yandex.ru', '{noop}password'),
       ('admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2);

INSERT INTO url (FULL_URL, SHORT_URL, USER_ID, END_DATE, COUNTER)
VALUES ('https://www.google.by', 'http://localhost:8080/UrlTrimmer/2bfy5zddkfn1jdh5rzd20', 1, now(), 0);