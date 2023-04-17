package com.zbinyds.central.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.zbinyds.central.pojo.Test;
import com.zbinyds.central.pojo.vo.TestUploadVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
@Slf4j
public class TestController {

    @GetMapping
    public String test(String id) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        return id + "===>" + date;
    }

    /**
     * 测试文件参数和其他参数一起接收 ==> 其一
     * @param test
     * @param file
     * @return
     */
    @PostMapping("/upload1")
    public boolean testUpload(Test test, MultipartFile file) {
        log.info("接口请求成功~~~~~~");
        log.info(file.getOriginalFilename() + "捕获成功！");
        log.info("test getId() ==>" + test.getId());
        log.info("test getQueryString() ==>" + test.getQueryString());
        log.info("test getCreateTime() ==>" + test.getCreateTime());
        log.info("test getUpdateTime() ==>" + test.getUpdateTime());
        log.info("接口请求完毕~~~~~~");
        return true;
    }

    @PostMapping("/upload2")
    public boolean testUpload(TestUploadVo vo) {
        log.info("接口请求成功~~~~~~");
        log.info(vo.getFiles()[0].getOriginalFilename() + "捕获成功！");
        log.info("test getId() ==>" + vo.getId());
        log.info("test getQueryString() ==>" + vo.getQueryString());
        log.info("test getCreateTime() ==>" + vo.getCreateTime());
        log.info("test getUpdateTime() ==>" + vo.getUpdateTime());
        log.info("接口请求完毕~~~~~~");
        return true;
    }

    @PostMapping("/upload3")
    public boolean testUpload(String test, MultipartFile[] files) {
        Test testObj = JSON.parseObject(test,Test.class);

        log.info("接口请求成功~~~~~~");
        log.info(files[0].getOriginalFilename() + "捕获成功！");
        log.info("test getId() ==>" + testObj.getId());
        log.info("test getQueryString() ==>" + testObj.getQueryString());
        log.info("test getCreateTime() ==>" + testObj.getCreateTime());
        log.info("test getUpdateTime() ==>" + testObj.getUpdateTime());
        log.info("接口请求完毕~~~~~~");
        return true;
    }
}
