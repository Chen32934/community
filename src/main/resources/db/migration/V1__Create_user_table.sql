CREATE  TABLE USER(
                      ID  INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
                      ACCOUNT_ID  VARCHAR(100),
                      NAME  VARCHAR(50),
                      TOKEN  VARCHAR(40),
                      GMT_CREATE  BIGINT,
                      GMT_MODIFIED  BIGINT,
                      BIO  VARCHAR(500)
)
