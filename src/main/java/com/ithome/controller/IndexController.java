package com.ithome.controller;

import com.ithome.domain.User;
import com.ithome.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private IUserMapper iUserMapper;


    @RequestMapping("/")
    public String Index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = iUserMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("userSession", user);
                    }
                    break;
                }
            }

        }
        return "index";
    }


}
