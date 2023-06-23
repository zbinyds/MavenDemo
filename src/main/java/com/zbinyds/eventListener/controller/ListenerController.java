package com.zbinyds.eventListener.controller;

import com.zbinyds.central.pojo.InterfaceRecords;
import com.zbinyds.central.service.InterfaceRecordsService;
import com.zbinyds.central.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.zbinyds.eventListener.controller
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/20 22:04
 */

@Slf4j
@RestController
@RequestMapping("/event-listener")
@RequiredArgsConstructor
public class ListenerController {

    private final InterfaceRecordsService interfaceRecordsService;

    @PostMapping("/test")
    public Result listener(@RequestBody InterfaceRecords entity) throws InterruptedException {
        interfaceRecordsService.insertEntity(entity);
        return Result.success();
    }


}
