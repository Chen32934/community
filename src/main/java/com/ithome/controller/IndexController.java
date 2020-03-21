package com.ithome.controller;


import com.ithome.dto.pagInationDTO;
import com.ithome.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String Index(HttpServletRequest request,Model model,
                         @RequestParam(name = "pageNum",required =true,defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "pageSize",required = true, defaultValue = "5") Integer pageSize
    ) {

        pagInationDTO pagInation = questionService.list(pageNum,pageSize);
        model.addAttribute("questionlist",pagInation);
        return "index";
    }


    @RequestMapping("/test")
    public String test(Model model) {
        String name="david";
        model.addAttribute("user",name);
        return "test";
    }


}
