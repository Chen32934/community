package com.ithome.controller;

import com.ithome.dto.AccessTokenDTO;
import com.ithome.dto.GithuUser;
import com.ithome.provider.GithubProider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProider githubProider;

    @Value("${github.client.id}")
    private String Client_id;

    @Value("${github.client.secret}")
    private String Client_secret;

    @Value("${github.Redirect.uri}")
    private String Redirect_uri;


//@RequestMapping(method = RequestMethod.GET)
    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(Redirect_uri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        String token = githubProider.getAccessToken(accessTokenDTO);
        GithuUser user=githubProider.getUser(token);
        System.out.println(user);
//githubProider.getAccessToken(new accessTokenDTO() );
        System.out.println(user.getName());
        return "index";

    }
}
