package com.zbinyds.central.strategy.verifyCode;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:49
 */

@Component
public class ShortMessageStrategyImpl implements VerifyCodeStrategy {

    @Override
    public String exec(HttpServletResponse response) {
        return "2";
    }

    @Override
    public VerifyCodeEnum getInstance() {
        return VerifyCodeEnum.SHOT_MESSAGE;
    }
}
