package com.zbinyds.central.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbinyds.asnyc.config.ThreadPoolConfig;
import com.zbinyds.asnyc.utils.BatchFuture;
import com.zbinyds.asnyc.utils.BatchFutureSupport;
import com.zbinyds.central.mapper.InterfaceRecordsMapper;
import com.zbinyds.central.pojo.InterfaceRecords;
import com.zbinyds.central.service.InterfaceRecordsService;
import com.zbinyds.eventListener.event.SpringMessageEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zbinyds
 * @description 针对表【t_interface_records】的数据库操作Service实现
 * @createDate 2023-05-20 20:00:47
 */
@Service
public class InterfaceRecordsServiceImpl extends ServiceImpl<InterfaceRecordsMapper, InterfaceRecords>
        implements InterfaceRecordsService {

    // 注入 spring 发布事件组件
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private InterfaceRecordsMapper interfaceRecordsMapper;

    @Resource(name = ThreadPoolConfig.EXECUTOR)
    private ThreadPoolTaskExecutor executor;

    @Transactional
    @Override
    public void insertEntity(InterfaceRecords entity) throws InterruptedException {
        interfaceRecordsMapper.insert(entity);
        LambdaQueryWrapper<InterfaceRecords> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InterfaceRecords::getId, entity.getId());
        update(InterfaceRecords.builder().info("zbinyds").build(), queryWrapper);
        Thread.sleep(5000);

        // 执行成功，触发回调接口
        applicationEventPublisher.publishEvent(new SpringMessageEvent<>(this, entity));

        // 模拟业务场景
        System.out.println("123 =======================> ");
    }

    @Override
    public List<InterfaceRecords> asyncSelectByIds(List<Long> ids) {
        // 异步批量查询
        BatchFuture<Map<Long, InterfaceRecords>> batchFuture = BatchFutureSupport.generalBatchFuture(
                executor.getThreadPoolExecutor(),
                ids,
                3,
                interfaceRecordsMapper::asyncSelectByIds,
                interfaceRecords -> interfaceRecords.getId() % 2 == 0
        );
        return new ArrayList<>(batchFuture.getUninterruptibly().values());
//        return new ArrayList<>(interfaceRecordsMapper.asyncSelectByIds(ids).values());
    }
}




