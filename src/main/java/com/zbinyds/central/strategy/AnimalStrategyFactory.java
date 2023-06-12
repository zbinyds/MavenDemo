package com.zbinyds.central.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
public class AnimalStrategyFactory {

    public static final HashMap<StrategyEnum, AnimalStrategy> data = new HashMap<>();

    private final AnimalStrategy[] strategyArray;

    @PostConstruct
    public void register() {
        Arrays.stream(strategyArray).forEach(strategy -> data.put(strategy.getInstance(), strategy));
    }

    public AnimalStrategy createStrategy(StrategyEnum strategyEnum) {
        return data.get(strategyEnum);
    }
}
