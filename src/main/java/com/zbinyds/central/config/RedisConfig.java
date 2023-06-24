package com.zbinyds.central.config;

import com.zbinyds.central.redis.ChannelEnum;
import com.zbinyds.central.redis.MessageReceiver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;


//配置类专属注解 并且完成自动注入
@Configuration
public class RedisConfig {

    /**
     * 定制化 redis序列化工具
     */
    public static class MyRedisSerializerCustomized extends GenericJackson2JsonRedisSerializer {
        /**
         * 序列化
         *
         * @param source object to serialize. Can be {@literal null}.
         * @return byte[]
         * @description 将 缓存对象 序列化 存储
         */
        @Override
        public byte[] serialize(Object source) throws SerializationException {
            // 如果缓存的资源不为null, 且类型为字符串或者字符, 序列化资源的字节数组
            if (Objects.nonNull(source)) {
                if (source instanceof String || source instanceof Character) {
                    return source.toString().getBytes();
                }
            }
            // 否则走父类指定的序列化方法
            return super.serialize(source);
        }

        /**
         * 反序列化
         *
         * @param source can be {@literal null}.
         * @param type   must not be {@literal null}.
         * @param <T>
         * @return
         * @throws SerializationException
         * @description 将 缓存数据 反序列化 为指定对象
         */
        @Override
        public <T> T deserialize(byte[] source, Class<T> type) throws SerializationException {
            Assert.notNull(type,
                    "Deserialization type must not be null! Please provide Object.class to make use of Jackson2 default typing.");
            if (source == null || source.length == 0) {
                return null;
            }
            // 如果需要反序列化的资源为字符串或者字符, 直接new String返回
            if (type.isAssignableFrom(String.class) || type.isAssignableFrom(Character.class)) {
                return (T) new String(source);
            }
            // 否则 走 父类指定的反序列化方法
            return super.deserialize(source, type);
        }
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 创建模板
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置序列化工具
        MyRedisSerializerCustomized jsonRedisSerializer =
                new MyRedisSerializerCustomized();
        // key和 hashKey采用 string序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value和 hashValue采用 JSON序列化
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     *
     * @param factory  Redis连接工厂
     * @param receiver 消息监听器
     * @return Redis消息监听容器
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                            MessageReceiver receiver) {
        // Redis消息监听容器
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置连接工厂
        container.setConnectionFactory(factory);
        // 向 容器内 添加 消息监听器
        container.addMessageListener(receiver, ChannelEnum.createPatternTopic(ChannelEnum.values()));
        return container;
    }
    /*
     *//**
     * 方式二
     * 向 容器内 注册 消息监听适配器
     * 绑定 消息处理器，利用 反射 调用消息处理器的业务方法
     *
     * @param receiver 消息接收器
     * @return 消息监听器
     *//*
    @Bean
    MessageListenerAdapter listenerAdapter(MessageReceiver receiver) {
        // 这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        // 也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }*/

    /**
     * 定制redis自动生成key策略
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