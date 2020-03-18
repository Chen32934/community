package com.ithome.controller;

import com.ithome.domain.Question;
import com.ithome.domain.User;
import com.ithome.mapper.IQuestionMapper;
import com.ithome.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publichController {

    @Autowired
    private IQuestionMapper iQuesstionMapper;

    @Autowired
    private IUserMapper iUserMapper;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }




    @PostMapping("/publish")
    public String addQuestion(@RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("tag") String tag, HttpServletRequest request, Model model) {
        User user = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            model.addAttribute("error", "请登录在发布");
            return "publish";
        } else {
            model.addAttribute("title", title);
            model.addAttribute("description", description);
            model.addAttribute("tag", tag);

            if (title == null || title=="" ) {
                model.addAttribute("error", "title不能为空");
                return "publish";
            }
            if (description == null || description=="" ) {
                model.addAttribute("error", "description不能为空");
                return "publish";
            }
            if (tag == null || tag=="") {
                model.addAttribute("error", "tag不能为空");
                return "publish";
            }

            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = iUserMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
            if (user == null) {
                model.addAttribute("error", "请登录在发布");
                return "publish";
            }

        }


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        iQuesstionMapper.create(question);
        return "redirect:/";
    }


}
