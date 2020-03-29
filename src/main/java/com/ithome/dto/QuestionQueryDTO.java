package com.ithome.dto;

import lombok.Data;

@Data
public class QuestionQueryDTO {

    private  String search;
    private  int pageSize;
    private  int pageNum;

}
