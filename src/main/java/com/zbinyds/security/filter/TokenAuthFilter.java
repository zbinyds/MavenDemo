package com.zbinyds.security.filter;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.zbinyds.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @Package: com.zbinyds.security.filter
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/29 21:30
 * <p>
 * w3c规定，请求头Authorization用于验证用户身份。这就是告诉我们，token应该写在请求头Authorization中
 * （当然你非要写在cookie，body或者写在url参数中也行，只要前后端开发约定好就行，但不建议）。
 * 那么互联网发展至今，认证方式也有很多种，所以w3c还规定，Authorization应当写成这样的形式Authorization: <type> <credentials>，
 * type是指认证的方式，credentials则是认证需要的信息。所以才有了jwt token的标准写法 Authorization: Bearer aaa.bbb.ccc。
 */

@Component
public class TokenAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenAuthFilter.class);

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization) || !JwtUtil.validateJWT(getTokenCredentials(authorization))) {
            // token不存在 或 token不合法
            filterChain.doFilter(request, response);
            return;
        }
        String token = getTokenCredentials(authorization);
        Claims claims = JwtUtil.parseJWT(token);
        log.info("用户: {}, 于{}, 进行操作", claims.get("username"), DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN));
        // todo token 解析完成表示 认证通过, 返回认证成功对象, 这里权限暂时设为 null
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims, claims.get("password"), null);
        // authenticationToken 写入 security 上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }

    // 截取字符串获取 token credentials
    public static String getTokenCredentials(String authorization) {
        return authorization.split(" ")[1];
    }

    // 截取字符串获取 authorization type
    public static String getAuthorizationType(String authorization) {
        return authorization.split(" ")[0];
    }
}
