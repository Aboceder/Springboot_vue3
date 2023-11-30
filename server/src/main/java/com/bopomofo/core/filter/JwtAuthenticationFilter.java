package com.bopomofo.core.filter;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getAccessToken(request).replace("Bearer ", "");
        StringBuffer requestURL = request.getRequestURL();

        if (StringUtils.hasText(accessToken)) {
            System.out.println("requestURL = " + requestURL);
            filterChain.doFilter(request, response);
        } else {
            throw new RuntimeException("令牌失效，请重新登陆");
        }
    }

    public String getAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

}
