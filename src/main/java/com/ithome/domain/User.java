package com.ithome.domain;


import lombok.Data;

@Data
public class User {

  private Integer id;
  private Integer accountId;
  private String name;
  private String token;
  private long gmtCreate;
  private long gmtModified;
  private String avatarUrl;
  private String bio;


}
