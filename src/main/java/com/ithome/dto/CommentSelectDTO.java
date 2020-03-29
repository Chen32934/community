package com.ithome.dto;

import com.ithome.domain.User;
import lombok.Data;

@Data
public class CommentSelectDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Integer commentCount;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private User user;
}
