package com.zbinyds.central.config;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    /**
     * 接收消息的方法
     */
    public void receiveMessage(String message) {
        System.out.println("收到一条来自zbinyds频道的消息：" + message);
    }

    /**
     * 接收消息的方法
     */
    public void receiveMessage2(String message) {
        System.out.println("收到一条chat2的消息：" + message);
    }

}