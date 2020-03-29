package com.ithome.controller;

import com.ithome.cach.TagCache;
import com.ithome.domain.Question;
import com.ithome.domain.QuestionExample;
import com.ithome.domain.User;
import com.ithome.exception.CustomizeErrorCode;
import com.ithome.exception.CustomizeException;
import com.ithome.mapper.QuestionMapper;
import org.apache.commons.lang3.StringUtils;
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
    private QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
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
        if (user == null) {
            model.addAttribute("error", "请登录在发布");
            return "publish";
        }
        if (title == null || title=="" ) {
            model.addAttribute("error", "title不能为空");
            return "publish";
        }
        if (description == null || description=="" ) {
            model.addAttribute("error", "description不能为空");
            return "publish";
        }
        if (tag == null || tag=="") {
//            model.addAttribute("error", "tag不能为空");
//            return "publish";
            throw  new CustomizeException(CustomizeErrorCode.TAG_NOT_NULL);
        }
        //非法标签
        String s = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(s)){
            model.addAttribute("error", "标签非法："+s);
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
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }else {
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(id);
            int i = questionMapper.updateByExampleSelective(question, questionExample);
            if (i!=1){
                throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            return "redirect:/profile/questions";
        }

        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String publishEdit(@PathVariable(name = "id") Integer id,Model model) {
        Question detail = questionMapper.selectByPrimaryKey(id);
//        Question detail = iQuesstionMapper.findQuestionDetailById(id);
        model.addAttribute("title", detail.getTitle());
        model.addAttribute("description", detail.getDescription());
        model.addAttribute("tag", detail.getTag());
        model.addAttribute("id",detail.getId());
        model.addAttribute("tags",TagCache.get());
        return "publish";
    }



}
