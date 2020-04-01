package com.ithome.controller;

import com.ithome.domain.User;
import com.ithome.domain.UserExample;
import com.ithome.dto.AccessTokenDTO;
import com.ithome.dto.GithuUser;
import com.ithome.mapper.IUserMapper;
import com.ithome.mapper.UserMapper;
import com.ithome.provider.GithubProider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@Slf4j  //追加日志
public class AuthorizeController {

    @Autowired
    private GithubProider githubProider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String Client_id;

    @Value("${github.client.secret}")
    private String Client_secret;

    @Value("${github.Redirect.uri}")
    private String Redirect_uri;


//@RequestMapping(method = RequestMethod.GET)
    @GetMapping("/callback")
    public String callBack(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state,
          HttpServletResponse rep
            ) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(Redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        String token = githubProider.getAccessToken(accessTokenDTO);
        GithuUser githuUser=githubProider.getUser(token);
        if (githuUser!=null){
            User user=new User();
            user.setName(githuUser.getName());
            user.setToken(token);
            user.setAccountId(Integer.parseInt(String.valueOf(githuUser.getId())));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githuUser.getAvatar_url());
            user.setBio(githuUser.getBio());
            //1.查询数据库是否有此用户的登陆数据 有更新；无插入
            //改动
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(String.valueOf(user.getAccountId()));
            List<User> users = userMapper.selectByExample(userExample);
            if (users.size()!=0){
                User DBuser=new User();
                DBuser.setName(githuUser.getName());
                DBuser.setToken(token);
                DBuser.setGmtModified(user.getGmtCreate());
                DBuser.setAvatarUrl(githuUser.getAvatar_url());
                UserExample userExample1 = new UserExample();
                userExample1.createCriteria().andAccountIdEqualTo(String.valueOf(user.getAccountId()));
                userMapper.updateByExampleSelective(DBuser,userExample1);
            }else {
                userMapper.insert(user);
            }
            //登陆OK  写入session 和cooike
            rep.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            log.error("callback get github error,{}",githuUser);
            return "redirect:/";
        }
//
    }


    @GetMapping("/logout")
   public String logout(HttpServletRequest request,HttpServletResponse response){

        request.getSession().removeAttribute("userSeesion");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        request.getSession().invalidate();
        return "redirect:/";

    }
}
