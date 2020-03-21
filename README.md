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
    ID            INTEGER not null,
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


  
