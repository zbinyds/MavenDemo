package com.zbinyds.central.strategy;

import org.springframework.stereotype.Component;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:58
 */

@Component
public class PeopleStrategyImpl implements AnimalStrategy {
    @Override
    public String exec() {
        return "码农正在努力搬砖...";
    }

    @Override
    public StrategyEnum getInstance() {
        return StrategyEnum.PEOPLE;
    }
}
