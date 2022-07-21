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
    id_user int,
    foreign key (id_user) references userprofile
    image varchar(200),
    primary key (id)
);

ALTER TABLE IF EXISTS public.post
    ADD COLUMN id_user integer references userprofile;

drop table feed;
create table feed(
	id int,
	follower int,
	selfUser int,
	primary key(id),
	foreign key(selfUser) references userprofile,
	foreign key(follower) references userprofile
);

create table commentary(
    reply varchar(50),
    id_post int,
    foreign key(id_post) references post
);
ALTER TABLE post ALTER COLUMN image TYPE varchar (150);
ALTER TABLE feed ALTER COLUMN follower TYPE int references userprofile;

select * from userprofile
insert into feed values (nextval('id_feed'),8,12)
select * from post
select * from feed
delete from post where image = 'Azeitona.jpg'
insert into post values (nextval('id_post'),'D:\UDESC\3 semestre\POO\TrabFinal\TrabFinal\Azeitona.jpg',12)
