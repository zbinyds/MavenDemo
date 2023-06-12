package com.zbinyds.central.strategy;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:48
 */
public interface AnimalStrategy {

    String exec();

    StrategyEnum getInstance();
}
