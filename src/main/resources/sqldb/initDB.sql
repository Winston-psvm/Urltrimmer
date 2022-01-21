-- DROP TABLE IF EXISTS user_roles;
-- DROP TABLE IF EXISTS url;
-- DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

create table users(
    id          integer         primary key DEFAULT nextval('global_seq') ,
    email       varchar         unique ,
    password    varchar         not null
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

create table url(
    id          integer         primary key ,
    full_url    varchar         unique ,
    short_url   varchar         unique ,
    user_id     integer         references users(id)
);