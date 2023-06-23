package com.zbinyds.central.redis;

import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Package: com.zbinyds.central.redis
 * @Author zbinyds@126.com
 * @Description: Redis发布订阅 - 频道枚举
 * @Create 2023/6/22 11:22
 */

public enum ChannelEnum {

    ZBINYDS_1("zbinyds1"),

    ZBINYDS_2("zbinyds2"),

    ZBINYDS_3("zbinyds3"),

    ;

    private String channelPattern;

    ChannelEnum() {
    }

    ChannelEnum(String channelPattern) {
        this.channelPattern = channelPattern;
    }

    public String getChannelPattern() {
        return channelPattern;
    }

    public void setChannelPattern(String channelPattern) {
        this.channelPattern = channelPattern;
    }

    /**
     * 根据 频道号 获取 频道枚举
     *
     * @param channelPattern 频道号
     * @return ChannelEnum
     */
    public static ChannelEnum getInstance(String channelPattern) {
        Assert.notNull(channelPattern, "channelPattern is not be null !");
        return Stream.of(ChannelEnum.values()).filter(channelEnum -> channelEnum.getChannelPattern().equals(channelPattern)).findFirst().orElse(null);
    }

    /**
     * 根据枚举 生成 PatternTopic
     *
     * @param enumArray 枚举数组
     * @return patternTopics
     */
    public static List<PatternTopic> createPatternTopic(ChannelEnum... enumArray) {
        Assert.notNull(enumArray, "args is not be null !");
        return Stream.of(enumArray).map(channelEnum -> PatternTopic.of(channelEnum.getChannelPattern())).collect(Collectors.toList());
    }
}
