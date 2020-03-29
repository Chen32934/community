# #   dawei

##  资料
[spring web](http://spring.io/guides)
[spring web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore)
[Github deploy key](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)
[BootStrap](https://v3.bootcss.com/getting-started/)
[Spring]()

##工具
[Git](https://git-scm.com/dowload)
[Visual Paradigm](https://www.visual-paradigm.com)
[lombok](https://projectlombok.org/)

##sql
CREATE  TABLE USER(
                      ID  INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
                      ACCOUNT_ID  VARCHAR(100),
                      NAME  VARCHAR(50),
                      TOKEN  VARCHAR(40),
                      GMT_CREATE  BIGINT,
                      GMT_MODIFIED  BIGINT,
                      BIO  VARCHAR(500),
                      AVATAR_URL VARCHAR(500)
)

create table QUESTION
(
    ID            INTEGER auto_increment not null,
    TITLE         VARCHAR(50),
    DESCRIPTION   CLOB,
    GMT_CREATE    BIGINT,
    GMT_MODIFIED  BIGINT,
    CREATOR       INTEGER,
    COMMENT_COUNT INTEGER default 0,
    VIEW_COUNT    INTEGER default 0,
    LIKE_COUNT    INTEGER default 0,
    TAG           VARCHAR(50),
    constraint QUESTION_PK
        primary key (ID)
);

create table COMMENT
(
    ID           BIGINT auto_increment,
    PARENT_ID    BIGINT  not null,
    TYPE         INTEGER,
    COMMENTATOR  INTEGER not null,
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
        COMMENT_COUNT INTEGER,
    LIKE_COUNT   BIGINT default 0,
    CONTENT      VARCHAR(500),
    constraint COMMENT_PK
        primary key (ID)
);
create table notify
(
	id int auto_increment,
	" informant" int,
	receiver int,
	outerId int,
	type int,
	gmt_create bigint,
	status int default 0,
	constraint notify_pk
		primary key (id)
);
alter table NOTIFY
    add NOTIFIRE_NAME varchar(100);

alter table NOTIFY
    add OUTER_TITLE VARCHAR(200);

comment on table notify is '通知';

comment on column notify.outerId is '类型的Id';






  
