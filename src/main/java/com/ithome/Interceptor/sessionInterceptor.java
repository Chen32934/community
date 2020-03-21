package com.ithome.Interceptor;

import com.ithome.domain.User;
import com.ithome.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    private IUserMapper iUserMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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

        }else {
            User user = null;
            request.getSession().setAttribute("userSession", user);

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
