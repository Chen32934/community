package com.ithome.dto;


import lombok.Data;

@Data
public class NotifyDTO {
    private Integer id;
    //枚举回复类型

    private Long gmtCreate;
    private Integer status;
    private String notifireName;
    private String outerTitle;

    private Integer outerid;
    private Integer type;
    private String typeName;
}
