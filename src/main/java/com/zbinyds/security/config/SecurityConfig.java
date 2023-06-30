package com.zbinyds.security.config;


import com.zbinyds.security.filter.TokenAuthFilter;
import com.zbinyds.security.handler.AccessRejectHandler;
import com.zbinyds.security.handler.AuthFailedEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    // token 认证过滤器
    private final TokenAuthFilter tokenAuthFilter;

    // 鉴权失败处理器
    private final AccessRejectHandler accessRejectHandler;

    // 认证失败处理器
    private final AuthFailedEntryPoint authFailedEntryPoint;

    /**
     * 解決
     * org.springframework.security.web.firewall.RequestRejectedException: The request was rejected because the URL contained a potentially malicious String "//"
     *
     * @return
     */
    @Bean
    public HttpFirewall httpFirewall() {
        // 设置请求防火墙
        return new StrictHttpFirewall();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthentication() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 关闭csrf防护
                .csrf().disable()
                // 调整策略，不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 不需要认证的请求，所有人都能访问
                .antMatchers("/sys/login", "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**").permitAll()
//                // 过滤PreFlight预检请求，也就是对于Preflight请求（浏览器同源检测请求）不做拦截
//                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 其他请求都需要认证
                .anyRequest().authenticated();
        // 自定义登录认证过滤器，在UsernamePasswordAuthenticationFilter拦截器之前执行
        http.addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // 自定义 认证失败处理器 以及 鉴权失败处理器
        http.exceptionHandling().accessDeniedHandler(accessRejectHandler).authenticationEntryPoint(authFailedEntryPoint);
        return http.build();
    }
}
