alter table users
change column email username varchar(500) charset utf8  not null;