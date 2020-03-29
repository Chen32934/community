package com.ithome.controller;

import com.ithome.domain.Comment;
import com.ithome.domain.User;
import com.ithome.dto.CommentDTO;
import com.ithome.dto.CommentSelectDTO;
import com.ithome.dto.ResultDTO;
import com.ithome.enums.CommentTypeEnum;
import com.ithome.exception.CustomizeErrorCode;
import com.ithome.exception.CustomizeException;

import com.ithome.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@ResponseBody
public class commentController {
    @Autowired
    private CommentService commentService;

    /**
     * 一级评论
     * RequestBody  自动把json数据传递大到DTO
     */

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("userSession");
        if (user == null) {
            throw new CustomizeException(CustomizeErrorCode.NOT_LOGIN);
        }
        if (commentDTO == null || StringUtils.isBlank(commentDTO.getContent())) {
            throw new CustomizeException(CustomizeErrorCode.CONTENT_NOT_NULL);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getAccountId());
        comment.setLikeCount(0);
        comment.setCommentCount(0);
        commentService.Insert(comment,user);
        return ResultDTO.okOf();
    }

    /**
     * 二级评论
     * @param id
     * @return
     */
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultDTO<List> comments(@PathVariable(name = "id") Integer id) {
        /**
         *  抽取参数
         */
        List<CommentSelectDTO> commentSelectDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentSelectDTOS);


    }


}
