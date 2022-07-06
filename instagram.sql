create sequence id_user;
create sequence id_post;
create sequence id_comment;
create sequence id_feed;
create sequence id_friends;

create table userprofile(
    id int,
    username varchar(30),
    password varchar(30),
    fullname varchar(30),
    bio varchar(50),
    primary key (id)
);

create table friends(
	id int,
	follower int,
	followed int,
	primary key(id),
	foreign key(follower) references userprofile,
	foreign key(followed) references userprofile
);

create table post(
    id int,
    image bytea[],
    primary key (id)
);

create table feed(
	id int,
	follower int,
	selfUser int,
	primary key(id),
	foreign key(selfUser) references userprofile,
	foreign key(follower) references post
);

create table commentary(
    reply varchar(50),
    id_post int,
    foreign key(id_post) references post
);