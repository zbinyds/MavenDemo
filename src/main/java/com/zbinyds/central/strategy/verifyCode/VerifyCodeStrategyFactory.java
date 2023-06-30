package com.zbinyds.central.strategy.verifyCode;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:59
 */

@Component
@RequiredArgsConstructor
public class VerifyCodeStrategyFactory implements InitializingBean {

    public static final HashMap<VerifyCodeEnum, VerifyCodeStrategy> data = new HashMap<>();

    private final VerifyCodeStrategy[] strategyArray;

    @Override
    public void afterPropertiesSet() {
        Arrays.stream(strategyArray).forEach(strategy -> data.put(strategy.getInstance(), strategy));
    }

    public VerifyCodeStrategy createStrategy(VerifyCodeEnum strategyEnum) {
        return data.get(strategyEnum);
    }
}
