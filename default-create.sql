create table img (
  id                        bigint auto_increment not null,
  caption                   varchar(255),
  _desc                     varchar(255),
  width                     integer,
  height                    integer,
  type                      varchar(255),
  ext                       varchar(255),
  hash                      varchar(255),
  deletehash                varchar(255),
  createdate                datetime,
  views                     bigint,
  votes                     double,
  votetotal                 bigint,
  votecount                 bigint,
  info                      varchar(255),
  constraint pk_img primary key (id))
;

create table tag (
  id                        bigint auto_increment not null,
  img_id                    bigint not null,
  name                      varchar(255),
  constraint pk_tag primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  login                     varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  constraint pk_user primary key (id))
;

alter table tag add constraint fk_tag_img_1 foreign key (img_id) references img (id) on delete restrict on update restrict;
create index ix_tag_img_1 on tag (img_id);


