package com.ithome.controller;


import com.ithome.dto.pagInationDTO;
import com.ithome.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {


    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String Index(Model model,
                        @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                        @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
                        @RequestParam(name = "search", required = false) String search
    ) {
        pagInationDTO pagInation = questionService.list(search, pageNum, pageSize);
        model.addAttribute("questionlist", pagInation);
        model.addAttribute("search",search);
        return "index";
    }

    @RequestMapping("/test")
    public String test(Model model) {
        return "makerdown";
    }


}
