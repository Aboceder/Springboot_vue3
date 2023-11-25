package com.bopomofo.core.config;

import com.bopomofo.core.handler.LoginFailureHandler;
import com.bopomofo.core.handler.LoginSuccessHandler;
import com.bopomofo.core.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .anyRequest().authenticated() // 拦截所有请求
                .and()
                .userDetailsService(userDetailsServiceImpl) // 自定义的用户校验逻辑
                .formLogin() // 表单登录
                .loginProcessingUrl("/api/member/login") // 登录路径
                .successHandler(new LoginSuccessHandler()) // 登录成功处理器
                .failureHandler(new LoginFailureHandler()) // 登录失败处理器
                .permitAll()
                .and()
                .logout() // 退出登录
                .logoutUrl("/api/member/logout") // 退出登录路径
                .permitAll() // 放行退出登录接口
                .and()
                .cors()
                .configurationSource(corsConfigurationSource()) // 放行请求跨域
                .and()
                .csrf().disable()
                .build();
    }

    public CorsConfigurationSource corsConfigurationSource() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //是否发送Cookie信息
        config.setAllowCredentials(true);
        //放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        //放行哪些原始域(头部信息)
        config.addAllowedHeader("*");
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        config.addExposedHeader("*");

        //2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的configSource.
        return configSource;
    }
}
