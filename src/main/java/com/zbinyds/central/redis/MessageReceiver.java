package com.zbinyds.central.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Redis消息接收
 * <p>
 * 方式一 实现 MessageListener 接口, 并注册到 RedisMessageListenerContainer 容器中, 通过onMessage回调方法实现; (个人推荐, 更灵活)
 * 方式二 不实现接口, 注册 MessageListenerAdapter 到 RedisMessageListenerContainer 容器中, 通过反射调用方法实现.
 */
@Service
@Slf4j
public class MessageReceiver implements MessageListener {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 接收消息
     */
    /*public void receiveMessage(Object messObj) {
        System.out.println("type => " + messObj instanceof String);
        System.out.println("收到一条来自zbinyds频道的消息：" + messObj);
    }*/

    /**
     * 回调函数
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = (String) redisTemplate.getKeySerializer().deserialize(message.getChannel());
        Object msg = redisTemplate.getValueSerializer().deserialize(message.getBody());
        switch (ChannelEnum.getInstance(channel)) {
            case ZBINYDS_1:
                log.info("频道zbinyds1 执行了...");
                break;
            case ZBINYDS_2:
                log.info("频道zbinyds2 执行了...");
                break;
            case ZBINYDS_3:
                log.info("频道zbinyds3 执行了...");
                break;
            default:
                log.error("枚举异常...");
                break;
        }
        log.info("channel => {}", channel);
        log.info("message  => {}", msg);
    }
}