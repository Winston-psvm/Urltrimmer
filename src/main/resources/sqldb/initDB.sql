create table users(
    id          integer         primary key ,
    email       varchar         unique ,
    password    varchar         not null ,
    role        varchar         not null
);

create table url(
    id          integer         primary key ,
    full_url    varchar         unique ,
    short_url   varchar         unique ,
    user_id     integer         references users(id)
);