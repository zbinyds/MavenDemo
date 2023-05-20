package com.zbinyds.central.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Parameter;
import java.util.*;


//配置类专属注解 并且完成自动注入
@Configuration
public class RedisConfig {

    /**
     * redis
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(new ObjectMapper());

        RedisSerializer stringSerializer = new StringRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);

        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param factory
     * @param listenerAdapter
     * @return
     */
    @Bean
    //相当于xml中的bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                            MessageListenerAdapter listenerAdapter
            /*MessageListenerAdapter listenerAdapter2*/) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        //订阅了一个叫 zbinyds 的频道
        container.addMessageListener(listenerAdapter, new PatternTopic("zbinyds"));
//        container.addMessageListener(listenerAdapter2, new PatternTopic("chat2"));
        //这个container 可以添加多个 messageListener
        return container;
    }

    /**
     * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
     *
     * @param receiver
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        //这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        //也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    /**
     * redis自动生成key策略
     */
    @Bean("keyGenerator")
    public KeyGenerator keyGenerator() {
        return (obj, method, params) -> {
            // 最终 生成 的 缓存 key 规则 ==> 接口名:参数1=参数1值,参数2=参数2值
            StringBuilder sb = new StringBuilder();
            sb.append(method.getName());
            sb.append(":");

            Arrays.stream(method.getParameters()).forEachOrdered(entity -> {
                // 这里重写 ArrayList中的 indexOf() 方法
                Integer index = null;
                Parameter[] parameters = method.getParameters();
                for (int i = 0; i < parameters.length; i++) {
                    if (entity.equals(parameters[i])) {
                        index = i;
                    }
                }
                if (index == null) index = -1;

                // 如果索引为 -1 的情况下, 可能发生异常
                try {
                    sb.append(entity.getName()).append("=").append(params[index]);
                    // 最后一个参数后面, 无需拼接逗号
                    if (index < parameters.length - 1) {
                        sb.append(",");
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Generate Redis Key Error!");
                }
            });

            return sb;
        };
    }
}