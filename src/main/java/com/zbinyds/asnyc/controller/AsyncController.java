package com.zbinyds.asnyc.controller;

import com.zbinyds.central.pojo.InterfaceRecords;
import com.zbinyds.central.service.InterfaceRecordsService;
import com.zbinyds.central.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Package: com.zbinyds.asnyc.controller
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/23 21:10
 */

@Slf4j
@RestController
@RequestMapping("/async")
@RequiredArgsConstructor
public class AsyncController {

    private final InterfaceRecordsService interfaceRecordsService;

    /**
     * 根据id列表异步查询
     */
    @GetMapping("/asyncSelectByIds")
    private Result<List<InterfaceRecords>> asyncTest(@RequestParam("ids") List<Long> ids) {
        List<InterfaceRecords> list = interfaceRecordsService.asyncSelectByIds(ids);
        return Result.success("查询成功~", list);
    }
}
