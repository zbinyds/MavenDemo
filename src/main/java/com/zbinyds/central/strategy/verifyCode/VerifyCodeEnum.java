package com.zbinyds.central.strategy.verifyCode;

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
public enum VerifyCodeEnum {

    IMAGE(1, "图片验证码"),

    SHOT_MESSAGE(2, "短信验证码"),

    MAILBOX(3, "邮箱验证码"),

    ;

    private final Integer type;

    private final String desc;

    /**
     * 根据 type 获取对应的枚举
     *
     * @param type 类别
     * @return StrategyEnum常量
     */
    public static VerifyCodeEnum getStrategy(Integer type) {
        return Arrays.stream(VerifyCodeEnum.values()).filter(strategyEnum -> strategyEnum.getType().equals(type)).findFirst().orElse(null);
    }
}
