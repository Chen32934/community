package com.ithome.dto;

import lombok.Data;

import java.util.List;

/**
 * 模拟标签
 */
@Data
public class TagDTO {
    private  String categoryName;
    private List<String> tags;
}
