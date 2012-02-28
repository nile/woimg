create table board (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  hash                      varchar(255),
  user_id                   bigint,
  category_id               bigint,
  create_date               datetime,
  constraint pk_board primary key (id))
;

create table category (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  code                      varchar(255),
  constraint pk_category primary key (id))
;

create table comment (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  comment                   varchar(255),
  create_date               datetime,
  paster_id                 bigint,
  constraint pk_comment primary key (id))
;

create table config (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  value                     varchar(255),
  constraint pk_config primary key (id))
;

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
  info                      varchar(255),
  source                    varchar(255),
  media                     varchar(255),
  url                       varchar(255),
  constraint pk_img primary key (id))
;

create table paster (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  img_id                    bigint,
  info                      varchar(255),
  board_id                  bigint,
  paste_date                datetime,
  hash                      varchar(255),
  parent_id                 bigint,
  repaste                   bigint,
  link                      varchar(255),
  constraint pk_paster primary key (id))
;

create table user (
  id                        bigint auto_increment not null,
  login                     varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  avatar                    varchar(255),
  name                      varchar(255),
  constraint pk_user primary key (id))
;

alter table board add constraint fk_board_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_board_user_1 on board (user_id);
alter table board add constraint fk_board_category_2 foreign key (category_id) references category (id) on delete restrict on update restrict;
create index ix_board_category_2 on board (category_id);
alter table comment add constraint fk_comment_user_3 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_comment_user_3 on comment (user_id);
alter table comment add constraint fk_comment_paster_4 foreign key (paster_id) references paster (id) on delete restrict on update restrict;
create index ix_comment_paster_4 on comment (paster_id);
alter table paster add constraint fk_paster_user_5 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_paster_user_5 on paster (user_id);
alter table paster add constraint fk_paster_img_6 foreign key (img_id) references img (id) on delete restrict on update restrict;
create index ix_paster_img_6 on paster (img_id);
alter table paster add constraint fk_paster_board_7 foreign key (board_id) references board (id) on delete restrict on update restrict;
create index ix_paster_board_7 on paster (board_id);
alter table paster add constraint fk_paster_parent_8 foreign key (parent_id) references paster (id) on delete restrict on update restrict;
create index ix_paster_parent_8 on paster (parent_id);


