create table if not exists file
(
    id        integer not null
        constraint file_pkey
            primary key,
    filename  varchar not null,
    file_size bytea[]
);

alter table file
    owner to root;

create table if not exists event
(
    id      integer not null
        constraint event_pkey
            primary key,
    name    varchar(255),
    file_id integer not null
        constraint file_id
            references file
);

alter table event
    owner to root;

create table if not exists "user"
(
    full_name varchar(255),
    id        integer not null
        constraint user_pkey
            primary key,
    event_id  integer not null
        constraint event_id
            references event
);

alter table users
    owner to root;

