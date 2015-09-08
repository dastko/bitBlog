# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table comment (
  id                        bigint auto_increment not null,
  post_id                   bigint,
  user_id                   bigint,
  content                   TEXT,
  date                      datetime,
  constraint pk_comment primary key (id))
;

create table post (
  id                        bigint auto_increment not null,
  content                   TEXT,
  user_id                   bigint,
  date                      datetime,
  title                     varchar(255),
  constraint pk_post primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varbinary(64) not null,
  registration              datetime,
  adress                    varchar(255),
  phone                     varchar(255),
  token                     varchar(255),
  validated                 tinyint(1) default 0,
  role                      ENUM('User', 'Customer', 'Admin'),
  constraint ck_user_role check (role in ('User','Customer','Admin')),
  constraint uq_user_email unique (email),
  constraint uq_user_token unique (token),
  constraint pk_user primary key (id))
;

alter table comment add constraint fk_comment_post_1 foreign key (post_id) references post (id) on delete restrict on update restrict;
create index ix_comment_post_1 on comment (post_id);
alter table comment add constraint fk_comment_user_2 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_comment_user_2 on comment (user_id);
alter table post add constraint fk_post_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_post_user_3 on post (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table comment;

drop table post;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

