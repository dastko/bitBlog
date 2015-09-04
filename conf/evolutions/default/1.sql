# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table post (
  id                        bigint auto_increment not null,
  title                     varchar(255),
  content                   TEXT,
  user_id                   bigint,
  date                      datetime,
  constraint pk_post primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  password                  varbinary(64) not null,
  registration              datetime,
  gender                    varchar(255),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;

alter table post add constraint fk_post_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_post_user_1 on post (user_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table post;

drop table user;

SET FOREIGN_KEY_CHECKS=1;

