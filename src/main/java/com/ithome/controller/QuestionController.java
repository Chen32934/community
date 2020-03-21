package com.ithome.controller;

import com.ithome.dto.QuestionDTO;
import com.ithome.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 问题详情
     * @param id
     * @return
     */
    @GetMapping("/question/{id}")
    public  String question(@PathVariable(name = "id") Integer id, Model model){
        QuestionDTO question = questionService.findQuestionDetailById(id);
        model.addAttribute("question",question);
        return "question";
    }
}
