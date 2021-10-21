create table if not exists data(
    id serial not null,
    primary key (id),
    incomeField1 varchar(45) ,
    incomeField2 varchar(45) ,
    incomeField3 varchar(45),
    incomeField4 varchar(45),
    outgoingField1 varchar(45),
    outgoingField2 varchar(45) ,
    outgoingField3 varchar(45),
    outgoingField4 varchar(45)
    );