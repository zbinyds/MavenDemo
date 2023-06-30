package com.zbinyds.central.aop;

import cn.hutool.core.convert.Convert;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.zbinyds.central.annotation.FrequencyControl;
import com.zbinyds.central.handler.GlobalException;
import com.zbinyds.central.util.Result;
import com.zbinyds.security.filter.TokenAuthFilter;
import com.zbinyds.security.util.JwtUtil;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Package: com.zbinyds.central.aop
 * @Author zbinyds@126.com
 * @Description: 接口频控 aop 实现
 * @Create 2023/6/27 20:11
 */

@Aspect
@Component
public class FrequencyAspect {

    // todo 后续redis key 统一存放于常量类中
    private static final String FREQUENCY_REDIS_KEY = "frequencyRedisKey::";

    private static final int INIT_COUNT = 1;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // 配置切入点。execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
    @Pointcut("execution(public * com.zbinyds.*.controller..*.*(..))")
    public void pointCut() {
    }

    @Around("pointCut() && @annotation(com.zbinyds.central.annotation.FrequencyControl)")
    @SneakyThrows
    public Object doAround(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // @AliasFor 注解必须搭配 Spring的工具类使用, 否则 注解不生效
        FrequencyControl frequencyControl = AnnotationUtils.getAnnotation(method, FrequencyControl.class);
        if (frequencyControl == null || frequencyControl.value() <= 0) {
            return joinPoint.proceed();
        }
        // 拼接 redis key
        String key = concatFrequencyRedisKey(request.getHeader("Authorization"), request.getRequestURI(), joinPoint.getArgs());

        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            // 如果不存在key, 则创建
            redisTemplate.opsForValue().set(key, INIT_COUNT, frequencyControl.interval(), frequencyControl.timeUnit());
            // 执行目标方法
            Result result = (Result) joinPoint.proceed();
            if (!result.getSuccess()) {
                // 目标方法执行不成功, 删除key
                redisTemplate.delete(key);
            }
            return result;
        } else {
            // 存在key
            // 判断是否达到指定访问频率
            Integer count = Convert.convert(Integer.class, redisTemplate.opsForValue().get(key));
            if (frequencyControl.count() == Objects.requireNonNull(count)) {
                throw new GlobalException(frequencyControl.message());
            }
            // 更新次数+1
            redisTemplate.opsForValue().increment(key);

            // 执行目标方法
            Result result = (Result) joinPoint.proceed();
            if (!result.getSuccess()) {
                // 更新次数-1
                redisTemplate.opsForValue().decrement(key);
            }
            return result;
        }
    }

    /**
     * redis key 生成规则: frequency_redis_key::{userId=用户id}:{uri=请求uri}:{params=请求参数hash值}
     *
     * @param token  登录凭证
     * @param uri    请求相对路径
     * @param params 请求参数
     * @return redis key
     */
    public static String concatFrequencyRedisKey(String token, String uri, Object[] params) {
        // 拼接参数
        StringBuilder sb = new StringBuilder();
        Arrays.stream(params).filter(param -> !(param instanceof HttpServletResponse)).forEach(obj -> {
            sb.append(Convert.convert(String.class, obj));
        });
        // 将请求参数进行hash md5加密
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String paramsMd5 = md5.digestHex(sb.toString());

        // 这里 userId 替换 token
        String credentials = TokenAuthFilter.getTokenCredentials(token);
        Long userId = JwtUtil.parseJWT(credentials).get("id", Long.class);

        return FREQUENCY_REDIS_KEY.concat("userId=" + userId)
                .concat(":").concat("uri=" + uri)
                .concat(":").concat("params=" + paramsMd5);
    }
}
