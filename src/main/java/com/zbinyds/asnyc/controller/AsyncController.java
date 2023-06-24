package com.zbinyds.asnyc.controller;

import cn.hutool.http.HttpUtil;
import com.zbinyds.central.pojo.InterfaceRecords;
import com.zbinyds.central.service.InterfaceRecordsService;
import com.zbinyds.central.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
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
    public Result<List<InterfaceRecords>> asyncTest(@RequestParam("ids") List<Long> ids) {
        List<InterfaceRecords> list = interfaceRecordsService.asyncSelectByIds(ids);
        return Result.success("查询成功~", list);
    }

    @GetMapping("/ip")
    public Result<String> ipAnalyze(@RequestParam(value = "ip", required = false) @NotBlank(message = "必填参数不能为空!") String ip) {
        // 通过淘宝接口解析得到ip归属地
        String body = HttpUtil.get("https://ip.taobao.com/outGetIpInfo?ip=" + ip + "&accessKey=alibaba-inc");
        log.info("body => {}", body);
        return Result.success("解析成功!", body);
    }
}
