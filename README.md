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

##sql
CREATE  TABLE USER(
                      ID  INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
                      ACCOUNT_ID  VARCHAR(100),
                      NAME  VARCHAR(50),
                      TOKEN  VARCHAR(40),
                      GMT_CREATE  BIGINT,
                      GMT_MODIFIED  BIGINT,
                      BIO  VARCHAR(500)
)
create table question
(
    id int not null,
    title varchar(50),
    description text,
    gmt_create BIGINT,
    gmt_modified BIGINT,
    creator int,
    comment_count int default 0,
    view_count int default 0,
    like_count int default 0,
    tag int,
    constraint question_pk
        primary key (id)
);

  
