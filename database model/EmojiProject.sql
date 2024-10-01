/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2024/10/2 4:50:14                            */
/*==============================================================*/


/*==============================================================*/
/* Table: admin_user                                            */
/*==============================================================*/
create table admin_user
(
   user_id              int not null,
   authority_level      int not null,
   user_name            varchar(128) not null,
   password             varchar(128),
   email                varchar(128),
   phone                varchar(32),
   primary key (user_id),
   unique key AK_Key_2 (user_name)
);

/*==============================================================*/
/* Table: client_user                                           */
/*==============================================================*/
create table client_user
(
   user_id              int not null,
   user_name            varchar(128) not null,
   password             varchar(128),
   email                varchar(128),
   phone                varchar(32),
   primary key (user_id),
   unique key AK_Key_2 (user_name)
);

/*==============================================================*/
/* Table: emoji_data                                            */
/*==============================================================*/
create table emoji_data
(
   serial_number        int not null auto_increment,
   user_id              int not null,
   emoji_type           int not null,
   time                 datetime not null,
   primary key (serial_number),
   constraint FK_EMOJI_DA_USER_ID_CLIENT_U foreign key (user_id)
      references client_user (user_id) on delete restrict on update restrict
);

