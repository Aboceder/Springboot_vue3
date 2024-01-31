package com.bopomofo.core.config;

import com.bopomofo.core.filter.JwtAuthenticationFilter;
import com.bopomofo.core.handler.authentication.AuthenticationFailureHandler;
import com.bopomofo.core.handler.authentication.LoginFailureHandler;
import com.bopomofo.core.handler.authentication.LoginSuccessHandler;
import com.bopomofo.core.handler.authentication.LogoutSuccessfulHandler;
import com.bopomofo.core.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Collections;

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
    public SecurityFilterChain filterChain(HttpSecurity http, PersistentTokenRepository repository) throws Exception {
        return http
                // 在用户名密码校验前面添加jwt校验的过滤器
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsServiceImpl), UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable) // 关闭csrf
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 禁用session
                })
                .authorizeRequests(auth -> {
                    auth.antMatchers("/github/**").permitAll(); // 允许匿名访问，只是不需要权限校验，路径依然会经过security的过滤器链
                    auth.anyRequest().authenticated(); // 拦截所有请求
                })
                .userDetailsService(userDetailsServiceImpl) // 自定义的用户校验逻辑
                .formLogin(login -> { // 表单登录
                    login.loginProcessingUrl("/api/member/login"); // 登录路径
                    login.successHandler(new LoginSuccessHandler()); // 登录成功处理器
                    login.failureHandler(new LoginFailureHandler()); // 登录失败处理器
                    login.permitAll(); // 允许匿名访问登录相关路径
                })
                .logout(logout -> { // 退出登录
                    logout.logoutUrl("/api/member/logout"); // 退出登录路径
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("bopomofo-rm");
                    logout.addLogoutHandler(new LogoutSuccessfulHandler());
                    logout.permitAll(); // 允许匿名访问退出登录接口
                })
                .rememberMe(remember -> {
                    // remember.rememberMeParameter("remember-me"); // 表单提交字段名称，默认“remember-me”
                    remember.tokenRepository(repository); // rememberMe持久化设置，自动保存到数据库中
                    remember.tokenValiditySeconds(3600 * 24 * 7); // 有效期设置为7天
                    remember.rememberMeCookieName("bopomofo-rm"); // 默认"remember-me"
                })
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource());// 跨域配置
                })
                .exceptionHandling(exception -> {
                    exception.authenticationEntryPoint(new AuthenticationFailureHandler());
                })

                .build();
    }

    /**
     * 跨域配置
     */
    public CorsConfigurationSource corsConfigurationSource() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        //是否发送Cookie信息
        config.setAllowCredentials(true);
        //放行哪些原始域(请求方式)
        config.addAllowedMethod(CorsConfiguration.ALL);
        //放行哪些原始域(头部信息)
        config.addAllowedHeader(CorsConfiguration.ALL);
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        config.addExposedHeader(CorsConfiguration.ALL);

        //2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的configSource.
        return configSource;
    }

    /**
     * rememberMe持久化设置，自动保存到数据库中
     * 用户调用logout方法后，或者token时间过期了，就会自动消除表中的对应数据
     */
    @Bean
    public PersistentTokenRepository tokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        // 第一次运行生成表，后面就不需要运行了
        // repository.setCreateTableOnStartup(true);
        repository.setDataSource(dataSource);
        return repository;
    }
}
