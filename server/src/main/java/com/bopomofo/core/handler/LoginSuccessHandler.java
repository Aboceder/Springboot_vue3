package com.bopomofo.core.handler;

import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.bopomofo.core.entity.RestBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        // 构建一个payload对象
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", user.getUsername());
        payload.put("expire_time", System.currentTimeMillis() + 5 * 60 * 1000);

        String accessToken = JWTUtil.createToken(payload, "hello world".getBytes());

        // 构建一个Map对象
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", accessToken);

        String jsonStr = JSONUtil.toJsonStr(tokens);

        // 返回成功响应给前端
        PrintWriter writer = response.getWriter();
        writer.write(JSONUtil.toJsonStr(RestBean.success(jsonStr)));
        writer.flush();
        writer.close();
    }
}
