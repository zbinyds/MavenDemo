package com.zbinyds.asnyc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig implements AsyncConfigurer {

    /**
     * 项目共用线程池
     */
    public static final String EXECUTOR = "zbinydsExecutor";

    @Override
    public Executor getAsyncExecutor() {
        return zbinydsExecutor();
    }

    @Bean(EXECUTOR)
    public ThreadPoolTaskExecutor zbinydsExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 线程池核心线程数
        executor.setCorePoolSize(10);
        // 线程池最大线程数量
        executor.setMaxPoolSize(10);
        // 队列容量
        executor.setQueueCapacity(200);
        // 线程池前缀
        executor.setThreadNamePrefix("zbinyds-executor-");
        // 拒绝策略
        /**
         * 1) AbortPolicy：丢弃新添加的task任务，并抛出异常;
         * 2) DiscardPolicy：丢弃新添加的task任务，啥也不做;
         * 3) DiscardOldestPolicy：丢弃任务队列中最先加入的task，并将新的task添加到任务队列中;
         * 4) CallerRunsPolicy：不进入线程池执行。由调用者线程去执行这个任务。(比如在main方法中，向线程池中添加task，那么此时由调用者main线程去处理这个新添加的task任务)。
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化线程池
        executor.initialize();
        return executor;
    }

}