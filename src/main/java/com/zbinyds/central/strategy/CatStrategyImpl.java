package com.zbinyds.central.strategy;

import org.springframework.stereotype.Component;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:54
 */

@Component
public class CatStrategyImpl implements AnimalStrategy {

    @Override
    public String exec() {
        return "小猫正在嬉闹..";
    }

    @Override
    public StrategyEnum getInstance() {
        return StrategyEnum.CAT;
    }

}
