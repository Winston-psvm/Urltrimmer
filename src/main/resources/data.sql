INSERT INTO users ( email, password)
VALUES ('user@yandex.ru', '{noop}password'),
       ('admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2);

INSERT INTO url (FULL_URL, SHORT_URL, USER_ID, END_DATE, COUNTER)
VALUES ('https://www.google.by', 'http://localhost:8080/UrlTrimmer/2bfy5zddkfn1jdh5rzd20', 1, now(), 0),

       ('https://ru.wikipedia.org/wiki/Чебурашка', 'http://localhost:8080/UrlTrimmer/cheburachka', 1, now(), 0),

       ('https://piteronline.tv/food/15-mest-peterburga-s-luchshej-shavermoj-2',
        'http://localhost:8080/UrlTrimmer/shaverma', 2, now(), 0),

       ('https://nord.codes', 'http://localhost:8080/UrlTrimmer/nordcodes', 2, now(), 0);