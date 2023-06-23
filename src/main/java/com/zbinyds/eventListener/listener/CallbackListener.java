package com.zbinyds.eventListener.listener;

import com.zbinyds.central.mapper.InterfaceRecordsMapper;
import com.zbinyds.central.pojo.InterfaceRecords;
import com.zbinyds.eventListener.event.SpringMessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;

/**
 * @Package: com.zbinyds.eventListener.listener
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/20 22:17
 * <p>
 * spring的事件监听 (具体使用看需求, 建议使用 @EventListener)
 * 1、使用@EventListener注解, 优点是 同一个listener可以监听多种不同类型事件;
 * 2。实现ApplicationListener接口, 但是只能监听同类型事件;
 */

@Slf4j
@Component
public class CallbackListener /*implements ApplicationListener<CallbackEvent>*/ {

    @Resource
    private InterfaceRecordsMapper interfaceRecordsMapper;

    /**
     * 异步处理 主业务外的业务
     *
     * @param event 事件
     */
    @Async
    @TransactionalEventListener(classes = SpringMessageEvent.class, fallbackExecution = true)
    public void callback(SpringMessageEvent<InterfaceRecords> event) throws InterruptedException {
        // 监听事件，进行回调操作
        InterfaceRecords records = event.getMsgObj();
        Thread.sleep(20000);
        log.info("最新records => {}", interfaceRecordsMapper.selectById(records.getId()));
    }

    /*@Override
    public void onApplicationEvent(CallbackEvent event) {
        // 监听时间，进行回调操作
        InterfaceRecords records = event.getInterfaceRecords();
        log.info("消息已被记录 => {}", records);
    }*/
}
