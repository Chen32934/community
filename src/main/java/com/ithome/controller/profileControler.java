package com.ithome.controller;


import com.ithome.domain.User;
import com.ithome.dto.pagInationDTO;

import com.ithome.service.NotifyService;
import com.ithome.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class profileControler {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model, HttpServletRequest request, @RequestParam(name = "pageNum", required = true, defaultValue = "1") Integer pageNum, @RequestParam(name = "pageSize", required = true, defaultValue = "5") Integer pageSize) {
        User user = (User) request.getSession().getAttribute("userSession");
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            pagInationDTO byIdPageList = questionService.findByIdPageList(user.getAccountId(), pageNum, pageSize);
            model.addAttribute("pagination", byIdPageList);
        }
        if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            pagInationDTO notifyPageList = notifyService.notifyListPage(user.getAccountId(), pageNum, pageSize);
            model.addAttribute("pagination", notifyPageList);
        }


        if (user == null) {
            return "redirect:/";
        }

        return "profile";
    }


}
