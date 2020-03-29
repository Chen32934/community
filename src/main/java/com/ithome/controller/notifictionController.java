package com.ithome.controller;

import com.ithome.domain.User;
import com.ithome.dto.NotifyDTO;
import com.ithome.enums.NotifyTypeEnum;
import com.ithome.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import javax.servlet.http.HttpServletRequest;


@Controller
public class notifictionController {

    @Autowired
    private NotifyService notifyService;

    @GetMapping("/notifiction/{id}")
    public String profile(@PathVariable(name = "id") Integer id, HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("userSession");

        if (user == null) {
            return "redirect:/";
        }
        NotifyDTO notifyDTO = notifyService.read(id, user);
        /**
         * 校验查看用户
         *
         */
        if (NotifyTypeEnum.Reply_Questions.getType() == notifyDTO.getType()
                || NotifyTypeEnum.Reply_Comment.getType() == notifyDTO.getType()) {
            return "redirect:/question/" + notifyDTO.getOuterid();
        }
        return "profile";
    }


}
