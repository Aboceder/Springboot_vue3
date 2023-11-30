package com.bopomofo.core.handler;

import cn.hutool.json.JSONUtil;
import com.bopomofo.core.entity.RestBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LogoutSuccessfulHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //User userDetails = (User) authentication.getPrincipal();
        // 关闭会话，清除缓存
//        request.getSession().invalidate();
//        // 设置为登录状态
//        authentication.setAuthenticated(false);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String json = JSONUtil.toJsonStr(RestBean.success("退出成功！！！"));
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();

    }
}
