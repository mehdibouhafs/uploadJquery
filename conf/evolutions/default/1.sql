# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table client (
  id                            integer not null,
  name                          varchar(255),
  constraint pk_client primary key (id)
);
create sequence client_seq;


# --- !Downs

drop table if exists client;
drop sequence if exists client_seq;

