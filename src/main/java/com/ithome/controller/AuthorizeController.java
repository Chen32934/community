package com.ithome.controller;

import com.ithome.domain.User;
import com.ithome.dto.AccessTokenDTO;
import com.ithome.dto.GithuUser;
import com.ithome.mapper.IUserMapper;
import com.ithome.provider.GithubProider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProider githubProider;

    @Autowired
    private IUserMapper iUserMapper;

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
            user.setAccountId(String.valueOf(githuUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            iUserMapper.InsertUser(user);
            //登陆OK  写入session 和cooike
            rep.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            return "redirect:/";
        }
//githubProider.getAccessToken(new accessTokenDTO() );
    }
}
