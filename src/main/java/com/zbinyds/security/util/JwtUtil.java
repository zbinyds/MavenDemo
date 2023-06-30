package com.zbinyds.security.util;

import cn.hutool.core.map.MapUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    // 密钥, 用于签发和验证JWT
    private static final String SECRET_KEY = "zbinyds";

    // 过期时间, 单位为毫秒, 默认为 一天
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // 生成JWT
    public static String createJWT(Map<String, Object> claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String createJWT(String subject) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 验证JWT并返回载荷中的主题（subject）
    public static boolean validateJWT(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // JWT验证失败
            log.error("Error Message: {}", e.getMessage());
            return false;
        }
    }

    // 解析JWT，返回其载荷（claims）
    public static Claims parseJWT(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    //

    // 示例
    @SneakyThrows
    public static void main(String[] args) {
        Map<String, Object> map = MapUtil.builder(new HashMap<String, Object>()).put("username", "zbin").put("password", "123456").build();
        String token = createJWT("1");
        System.out.println("token ===" + token);
        Claims claims = parseJWT(token);
        System.out.println(claims.get("username"));
        System.out.println(claims.getSubject());
        boolean flag = validateJWT(token);
        System.out.println("flag ===" + flag);
    }
}