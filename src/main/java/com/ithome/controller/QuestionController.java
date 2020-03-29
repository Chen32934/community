package com.ithome.controller;

import com.ithome.dto.CommentSelectDTO;
import com.ithome.dto.QuestionDTO;
import com.ithome.enums.CommentTypeEnum;
import com.ithome.service.CommentService;
import com.ithome.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    /**
     * 问题详情
     * @param id
     * @return
     */
    @GetMapping("/question/{id}")
    public  String question(@PathVariable(name = "id") Integer id, Model model){

        QuestionDTO question = questionService.findQuestionDetailById(id);
        //查询相关问题
        List<QuestionDTO> selectRelated=questionService.selectRelated(question);
        //查询问题评论
        List<CommentSelectDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.IncView(id);
        model.addAttribute("question",question);
        model.addAttribute("comments",comments);
        model.addAttribute("selectRelated",selectRelated);
        return "question";
    }





}
