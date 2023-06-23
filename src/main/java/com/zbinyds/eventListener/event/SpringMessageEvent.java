package com.zbinyds.eventListener.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @Package: com.zbinyds.eventListener.event
 * @Author zbinyds@126.com
 * @Description: 通用的 spring event
 * @Create 2023/6/20 22:13
 */

@Getter
public class SpringMessageEvent<T> extends ApplicationEvent {

    private static final long serialVersionUID = -8475649734395619297L;

    private final T msgObj;

    public SpringMessageEvent(Object source, T msgObj) {
        super(source);
        this.msgObj = msgObj;
    }
}
