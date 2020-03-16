package com.ithome.provider;

import com.alibaba.fastjson.JSON;
import com.ithome.dto.AccessTokenDTO;
import com.ithome.dto.GithuUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        // MediaType指的是要传递的数据的MIME类型
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过access_token获取到用户的信息
     *
     * @param access_token
     * @return
     */
    public GithuUser getUser(String access_token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + access_token).build();
        try (
                Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //将string的Json对象转换为GithuUser类的对象
            GithuUser githuUser = JSON.parseObject(string, GithuUser.class);
            return githuUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
