create table user
(
    id int auto_increment,
    name varchar(255) not null,
    age integer not null,
    password varchar(32) not null,
    create_at timestamp not null,
    primary key(id)
);

create unique index user_id_uindex
    on user (id);

create table admin
(
    id int auto_increment,
    name varchar(255) not null,
    password varchar(32) not null,
    primary key(id)
);

create unique index admin_id_uindex
    on admin (id);

create table message
(
    id int auto_increment,
    text varchar(2000) not null,
    user_from_id int not null,
    user_to_id int not null,
    create_at timestamp not null,
    primary key(id),
    foreign key (user_from_id) references user(id),
    foreign key (user_to_id) references user(id)
);

create unique index message_id_uindex
    on message (id);