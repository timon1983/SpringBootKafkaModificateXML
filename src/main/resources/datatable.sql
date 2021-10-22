create table if not exists data
(
    id        serial not null,
    primary key (id),
    type      varchar(45),
    in_value  varchar(45),
    out_value varchar(45)
);

alter table data
    add column version int;
alter table data
    alter column version set not null;

create table if not exists topics
(
    id         serial not null,
    primary key (id),
    read_topic varchar(255),
    send_topic varchar(255)
);

create table if not exists messages
(
    id          serial not null,
    primary key (id),
    in_message  varchar,
    out_message varchar
);
