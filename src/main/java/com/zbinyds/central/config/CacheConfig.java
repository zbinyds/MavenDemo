package com.zbinyds.central.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Package: com.zbinyds.central.config
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/24 21:53
 */

@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Caffeine(本地缓存), 默认使用
     */
    @Bean("caffeineCacheManager")
    @Primary
    public CacheManager caffeineCacheManager() {
        // 构建 caffeine
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                // 缓存过期时间
                .expireAfterWrite(5, TimeUnit.MINUTES)
                // 初始化容量
                .initialCapacity(100)
                // 最大缓存数量
                .maximumSize(200);

        // 定制化缓存Cache
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }

    /**
     * Redis缓存
     */
    @Bean("redisCacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory factory) {
        return RedisCacheManager.create(factory);
    }
}
