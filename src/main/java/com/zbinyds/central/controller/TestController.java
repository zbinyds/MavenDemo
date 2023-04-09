package com.zbinyds.central.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Package: com.zbinyds.central.controller
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/4/9 18:38
 */


@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    @GetMapping
    public String test(String id){
        String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        return id + "===>" + date;
    }
}
