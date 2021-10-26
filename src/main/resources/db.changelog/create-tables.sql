create table if not exists data(
                                  id serial not null,
                                  primary key (id),
                                  type varchar(45) not null,
                                  in_value varchar(45),
                                  out_value varchar(45) ,
                                  version int not null default 0
    );
create table if not exists topics(
                                  id serial not null,
                                  primary key (id),
                                  read_topic varchar(100) not null,
                                  send_topic varchar(100) not null
    );

create table if not exists messages(
                                  id serial not null,
                                  primary key (id),
                                  in_message varchar ,
                                  out_message varchar
    );
