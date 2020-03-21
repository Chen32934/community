package com.ithome.controller;

import com.ithome.domain.Question;
import com.ithome.domain.User;
import com.ithome.mapper.IQuestionMapper;
import com.ithome.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class publichController {

    @Autowired
    private IQuestionMapper iQuesstionMapper;




    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }


    @PostMapping("/publish")
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("tag") String tag,
                              @RequestParam("id") Integer id,
                              HttpServletRequest request,
                              Model model) {

        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        User user= (User) request.getSession().getAttribute("userSession");
        long currentTimeMillis = System.currentTimeMillis();
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
        if (user == null) {
            model.addAttribute("error", "请登录在发布");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(Integer.valueOf(user.getAccountId()));
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(System.currentTimeMillis());
        if (id==null){
            iQuesstionMapper.create(question);
        }else {
            question.setId(id);
            iQuesstionMapper.updateQuestion(question);
            return "redirect:/profile/questions";
        }

        return "redirect:/";
    }



    @GetMapping("/publish/{id}")
    public String publishEdit(@PathVariable(name = "id") Integer id,Model model) {
        Question detail = iQuesstionMapper.findQuestionDetailById(id);
        model.addAttribute("title", detail.getTitle());
        model.addAttribute("description", detail.getDescription());
        model.addAttribute("tag", detail.getTag());
        model.addAttribute("id",detail.getId());
        return "publish";
    }



}
