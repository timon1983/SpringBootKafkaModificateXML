create table if not exists dataXML(
    id serial not null,
    primary key (id),
    type varchar(45) ,
    in_value varchar(45) ,
    out_value varchar(45)
    );