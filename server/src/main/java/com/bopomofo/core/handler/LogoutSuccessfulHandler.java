package com.bopomofo.core.handler;

import cn.hutool.json.JSONUtil;
import com.bopomofo.core.entity.RestBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LogoutSuccessfulHandler implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            // 设置为登录状态
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            String json = JSONUtil.toJsonStr(RestBean.success("退出成功！！！！！！！！"));
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
