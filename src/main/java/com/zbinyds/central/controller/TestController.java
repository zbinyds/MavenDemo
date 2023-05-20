package com.zbinyds.central.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zbinyds.central.pojo.Test;
import com.zbinyds.central.pojo.vo.TestUploadVO;
import com.zbinyds.central.service.TestService;
import com.zbinyds.central.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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

    private final TestService testService;

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
    public boolean testUpload(TestUploadVO vo) {
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
        return testService.list();
    }


    /**
     * mybatis - 分页查询
     * 使用 pageHelper 完成分页查询
     */
    @Cacheable(value = "QS", keyGenerator = "keyGenerator")
    @GetMapping("/findAllByMybatis")
    public Result findAllByMybatis(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Test> list = testService.list();
        PageInfo<Test> pageInfo = new PageInfo<>(list);
        return Result.success("查询成功~", pageInfo);
    }

    /**
     * mybatis - 分页查询
     * 使用 pageHelper 完成分页查询
     */
    @Cacheable(value = "QS", keyGenerator = "keyGenerator")
    @GetMapping("/findAllByMybatisPlus")
    public Result findAllByMybatisPlus(Integer pageNum, Integer pageSize, String queryString) {
        // 构建返回的 data
        return Result.success("查询成功~").of(testService.pageByQueryString(pageNum, pageSize, queryString));
    }
}
