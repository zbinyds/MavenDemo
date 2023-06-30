package com.zbinyds.central.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.zbinyds.central.annotation
 * @Author zbinyds@126.com
 * @Description: 接口调用频率限制
 * @Create 2023/6/27 19:58
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FrequencyControl {

    /**
     * 间隔时间(ms)
     */
    @AliasFor("interval")
    int value() default 3000;

    @AliasFor("value")
    int interval() default 3000;

    /**
     * 时间单位, 默认为毫秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 给定时间间隔内, 能够请求的次数
     */
    int count() default 1;

    /**
     * 提示信息
     */
    String message() default "接口繁忙, 请稍后再试!";
}
