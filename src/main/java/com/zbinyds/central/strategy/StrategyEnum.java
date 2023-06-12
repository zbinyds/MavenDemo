package com.zbinyds.central.strategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:32
 */

@Getter
@AllArgsConstructor
public enum StrategyEnum {

    DOG(1, "狗"),

    CAT(2, "猫"),

    PEOPLE(3, "人"),

    ;

    private final Integer code;

    private final String desc;

    /**
     * 根据type类别获取相应的枚举
     * @param type 前端传递而来的类别
     * @return StrategyEnum常量
     */
    public static StrategyEnum getStrategy(Integer type) {
        return Arrays.stream(StrategyEnum.values()).filter(strategyEnum -> strategyEnum.getCode().equals(type)).findFirst().orElse(null);
    }
}
