/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2024/10/2 4:50:14                            */
/*==============================================================*/

/*==============================================================*/
/* Table: admin_user                                            */
/*==============================================================*/
CREATE TABLE admin_user (
    id int NOT NULL auto_increment,
    authorityLevel int NOT NULL,
    username varchar(128) NOT NULL,
    password varchar(128),
    email varchar(128),
    phone varchar(32),
    PRIMARY KEY (id),
    UNIQUE KEY AK_Key_2 (username)
);

/*==============================================================*/
/* Table: client_user                                           */
/*==============================================================*/
CREATE TABLE client_user (
    id int NOT NULL auto_increment,
    username varchar(128) NOT NULL,
    password varchar(128),
    email varchar(128),
    phone varchar(32),
    PRIMARY KEY (id),
    UNIQUE KEY AK_Key_2 (username)
);

/*==============================================================*/
/* Table: emoji_data                                            */
/*==============================================================*/
CREATE TABLE emoji_data (
    serialNumber int NOT NULL auto_increment,
    id int NOT NULL,
    type int NOT NULL,
    TIME datetime NOT NULL,
    PRIMARY KEY (serialNumber),
    CONSTRAINT FK_EMOJI_DA_USER_ID_CLIENT_U FOREIGN KEY (id) REFERENCES client_user (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);