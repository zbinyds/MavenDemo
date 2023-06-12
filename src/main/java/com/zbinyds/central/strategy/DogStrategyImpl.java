package com.zbinyds.central.strategy;

import org.springframework.stereotype.Component;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:49
 */

@Component
public class DogStrategyImpl implements AnimalStrategy {

    @Override
    public String exec() {
        return "小狗正在奔跑..";
    }

    @Override
    public StrategyEnum getInstance() {
        return StrategyEnum.DOG;
    }
}
