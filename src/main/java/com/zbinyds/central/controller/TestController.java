package com.zbinyds.central.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.zbinyds.central.mapper.TestMapper;
import com.zbinyds.central.pojo.Test;
import com.zbinyds.central.pojo.vo.TestUploadVo;
import com.zbinyds.central.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
@RequiredArgsConstructor
public class TestController {

    private final TestMapper testMapper;

    @GetMapping
    public String test(String id) {
        String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        return id + "===>" + date;
    }

    /**
     * 测试文件参数和其他参数一起接收 ==> 其一
     *
     * @param test
     * @param file
     * @return
     */
    @PostMapping("/upload1")
    public boolean testUpload(Test test, MultipartFile file) {
        log.info("接口请求成功~~~~~~");
        log.info(file.getOriginalFilename() + "捕获成功！");
        log.info("test getId() ==>" + test.getId());
        log.info("test getQueryString() ==>" + test.getQuerystring());
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
        log.info("test getQueryString() ==>" + vo.getQuerystring());
        log.info("test getCreateTime() ==>" + vo.getCreateTime());
        log.info("test getUpdateTime() ==>" + vo.getUpdateTime());
        log.info("接口请求完毕~~~~~~");
        return true;
    }

    @PostMapping("/upload3")
    public boolean testUpload(String test, MultipartFile[] files) {
        Test testObj = JSON.parseObject(test, Test.class);

        log.info("接口请求成功~~~~~~");
        log.info(files[0].getOriginalFilename() + "捕获成功！");
        log.info("test getId() ==>" + testObj.getId());
        log.info("test getQueryString() ==>" + testObj.getQuerystring());
        log.info("test getCreateTime() ==>" + testObj.getCreateTime());
        log.info("test getUpdateTime() ==>" + testObj.getUpdateTime());
        log.info("接口请求完毕~~~~~~");
        return true;
    }

    @GetMapping("/findAllTest")
    public List<Test> findAllTest() {
        return testMapper.selectAll();
    }

    /**
     * 查询queryString中包含zbin的记录
     */
    @Cacheable(value = "QS", keyGenerator = "keyGenerator")
    @GetMapping("/findZbinList")
    public Result findZbinList(String queryString) {
        Map<Integer, Test> data = testMapper.findZbinList(queryString).stream().collect(Collectors.toMap(Test::getId, Function.identity()));
        return Result.success("查询成功~").of(data);
    }
}
