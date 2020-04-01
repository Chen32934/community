package com.ithome.Interceptor;

import com.ithome.domain.Notify;
import com.ithome.domain.User;
import com.ithome.domain.UserExample;
import com.ithome.mapper.IUserMapper;
import com.ithome.mapper.UserMapper;
import com.ithome.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class sessionInterceptor implements HandlerInterceptor {

    /**
     * 程序处理之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotifyService notifyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size()!=0) {
                        request.getSession().setAttribute("userSession", users.get(0));
                        Integer unreadCount = notifyService.unreadCount(users.get(0).getAccountId());
                        request.getSession().setAttribute("unreadSession",unreadCount);
                    }
                    break;
                }
            }

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
