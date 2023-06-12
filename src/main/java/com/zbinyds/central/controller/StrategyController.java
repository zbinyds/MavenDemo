package com.zbinyds.central.controller;

import com.zbinyds.central.service.TestService;
import com.zbinyds.central.strategy.AnimalStrategy;
import com.zbinyds.central.strategy.AnimalStrategyFactory;
import com.zbinyds.central.strategy.StrategyEnum;
import com.zbinyds.central.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.zbinyds.central.controller
 * @Author zbinyds@126.com
 * @Description: 策略+工厂+枚举, 减少if-else代码小case
 * @Create 2023/6/12 20:23
 */

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/strategy")
public class StrategyController {

    private final TestService testService;

    private final AnimalStrategyFactory animalStrategyFactory;

    /**
     * 模拟场景：type=1, 执行一段业务逻辑; type=2, 执行另外一段业务逻辑; 以此类推...
     * @param type 前端传递而来的类别
     * @return 接口执行结果
     */
    @GetMapping("/test")
    public Result strategyCase(Integer type) {
        // 1、使用前端传递而来的type字段, 获取枚举常量;
        StrategyEnum strategyEnum = StrategyEnum.getStrategy(type);

        // 2、根据枚举常量, 获取相应的执行策略
        AnimalStrategy strategy = animalStrategyFactory.createStrategy(strategyEnum);

        // 3、执行策略, 获取执行结果
        String data = strategy.exec();

        return Result.success("执行成功~", data);
    }
}
