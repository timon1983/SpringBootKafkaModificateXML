create table if not exists data(
    id serial not null,
    primary key (id),
    type varchar(45) ,
    in_value varchar(45) ,
    out_value varchar(45)
    );

alter table data add column version int ;
alter table data alter column version set not null;