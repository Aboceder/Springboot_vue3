package com.bopomofo.core.config;

import com.bopomofo.core.handler.LoginFailureHandler;
import com.bopomofo.core.handler.LoginSuccessHandler;
import com.bopomofo.core.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

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
                .authorizeHttpRequests(auth -> {
                    // auth.antMatchers("/path/**").permitAll(); // 如果需要放行其他请求，在上面写这个
                    auth.anyRequest().authenticated(); // 拦截所有请求
                })
                .userDetailsService(userDetailsServiceImpl) // 自定义的用户校验逻辑
                .formLogin(login -> { // 表单登录
                    login.loginProcessingUrl("/api/member/login"); // 登录路径
                    login.successHandler(new LoginSuccessHandler()); // 登录成功处理器
                    login.failureHandler(new LoginFailureHandler()); // 登录失败处理器
                    login.permitAll(); // 放行登录相关路径
                })
                .logout(logout -> { // 退出登录
                    logout.logoutUrl("/api/member/logout"); // 退出登录路径
                    logout.permitAll(); // 放行退出登录接口
                })
                .cors(cors -> {
                    cors.configurationSource(corsConfigurationSource()); // 放行请求跨域
                })
                .rememberMe(remember -> {
                    remember.alwaysRemember(false); // 是否勾选记住我，默认false
                    remember.rememberMeParameter("remember-me"); // 表单提交字段名称，默认“remember-me”
                    remember.rememberMeCookieName("remember-token"); // 记住我cookie的名称
                    remember.tokenRepository(repository); // rememberMe持久化设置，自动保存到数据库中
                    remember.tokenValiditySeconds(3600 * 24 * 7); // 有效期设置为7天
                })
                .csrf(AbstractHttpConfigurer::disable) // 关闭csrf
                .build();
    }

    /**
     * 跨域配置
     */
    private CorsConfigurationSource corsConfigurationSource() {
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
