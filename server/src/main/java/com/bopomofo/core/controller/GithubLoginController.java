package com.bopomofo.core.controller;

import cn.hutool.http.HttpUtil;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/github")
public class GithubLoginController {

    @Value("${justAuth.clientId.github}")
    private String CLIENT_ID;

    @Value("${justAuth.clientSecret.github}")
    private String CLIENT_SECRET;

    @GetMapping("/callback")
    public String callback(String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("client_id", CLIENT_ID);
        map.put("client_secret", CLIENT_SECRET);
        map.put("code", code);
        return HttpUtil.post("https://github.com/login/oauth/access_token", map);
    }

    @GetMapping("/callback/getCode")
    public void getCode(String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("client_id", CLIENT_ID);
        map.put("client_secret", CLIENT_SECRET);
        map.put("code", code);
        String post = HttpUtil.post("https://github.com/login/oauth/access_token", map);
        System.out.println("post = " + post);
    }

    /**
     * 获取GitHub客户端id
     */
    @GetMapping("/getGithubClientId")
    public List<String> getGithubClientId() {
        String randomStr = RandomString.make(8);
        return Arrays.asList(CLIENT_ID, randomStr);
    }

}
