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

import javax.servlet.http.HttpServletRequest;
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
            HttpServletRequest req
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
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(githuUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            iUserMapper.InsertUser(user);
        req.getSession().setAttribute("userSession", githuUser);
            return "redirect:index";
        }else {
            return "redirect:index";
        }
//githubProider.getAccessToken(new accessTokenDTO() );
    }
}
