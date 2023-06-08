package com.zbinyds.central.functionalInterface;

/**
 * @Package: com.zbinyds.central.functionalInterface
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/5/23 23:49
 */

@FunctionalInterface
public interface SendMessage<T, R> {
    R send(T t);
}
