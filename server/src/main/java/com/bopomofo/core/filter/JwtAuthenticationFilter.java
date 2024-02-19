package com.bopomofo.core.filter;

import com.bopomofo.core.service.UserDetailsServiceImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// @Component // 使用了component注解后，过滤器会被注册到Spring容器中，导致filter被spring跟security执行两次
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JwtAuthenticationFilter(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
/*        // 判断ignoreToken
        boolean ignoreToken = Boolean.parseBoolean(request.getHeader("ignoreToken"));
        if (!ignoreToken) {
            // 从请求头中获取token
            String token = request.getHeader("Authorization");
            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                token = token.replace("Bearer ", "");
                //todo 校验token的合法性

                // 解析token中的username
                String username = JWTUtil.parseToken(token).getPayload("username").toString();
                // 获取登录用户详情
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                // 将用户授权信息保存到security上下文中，供后续的业务逻辑使用
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }*/
        // 放行
        filterChain.doFilter(request, response);
    }
}
