package com.ithome.dto;

import lombok.Data;

import java.util.List;

/**
 * 模拟标签数据库
 */
@Data
public class IndexTagDTO {
    private  String categoryName;
    private List<pagInationDTO> pagInationDTO;
}
