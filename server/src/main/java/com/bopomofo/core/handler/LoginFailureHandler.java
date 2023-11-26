package com.bopomofo.core.handler;

import cn.hutool.json.JSONUtil;
import com.bopomofo.core.entity.ErrorInfo;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String message = "";
        if (exception instanceof LockedException) {
            message = "账户被锁定,请与联系管理员处理";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "密码过期，请与联系管理员处理!";
        } else if (exception instanceof AccountExpiredException) {
            message = "账户过期，请与联系管理员处理!";
        } else if (exception instanceof DisabledException) {
            message = "账户被禁用，请与联系管理员处理!";
        } else if (exception instanceof UsernameNotFoundException) {
            message = exception.getMessage();
        } else {
            message = exception.getMessage();
        }
        writer.write(JSONUtil.toJsonStr(ErrorInfo.failure(401, message)));
        writer.flush();
        writer.close();
    }
}
