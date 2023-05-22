package com.zbinyds.central.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zbinyds.central.pojo.Test;
import com.zbinyds.central.pojo.vo.TestUploadVO;
import com.zbinyds.central.service.TestService;
import com.zbinyds.central.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Package: com.zbinyds.central.controller
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/4/9 18:38
 */


@CacheConfig(cacheNames = "TestController")
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
     * 注意：项目中不能同时使用 pageHelper和 page，否则 mybatis-plus的page分页会报错
     */
//    @GetMapping("/findAllByMybatis")
//    public Result findAllByMybatis(Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<Test> list = testService.list();
//        PageInfo<Test> pageInfo = new PageInfo<>(list);
//        return Result.success("查询成功~", pageInfo);
//    }

    /**
     * mybatisPlus - 分页查询
     * 使用 page 完成分页查询
     */
    @GetMapping("/findAllByMybatisPlus")
    public Result findAllByMybatisPlus(Integer pageNum, Integer pageSize, String queryString) {
        // 构建返回的 data
        return Result.success("查询成功~").of(testService.pageByQueryString(pageNum, pageSize, queryString));
    }

    @PostMapping
    public Result insert(@RequestBody Test test) {
        return testService.save(test) ? Result.success("操作成功~") : Result.failed("操作失败！");
    }

    @CacheEvict(key = "#test.id")
    @PutMapping
    public Result update(@RequestBody Test test) {
        // 这里 update() 方法内的参数 QueryWrapper 没有做限制 可以是 QueryWrapper 也可以是 UpdateWrapper 但是 前者无法使用方式二

        // 方式一 直接使用 lambdaQueryWrapper 作为条件，进行修改。 使用 update(entity, QueryWrapper)
        LambdaQueryWrapper<Test> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Objects.nonNull(test.getId()), Test::getId, test.getId());
//        testService.update(test, queryWrapper1);

        // Wrappers.lambdaXXX(test) 中 传入的 entity 为 where条件 , 因此 这里需要做限制 如果传入条件为 null 默认没有修改条件 则会将表中的所有记录同步修改。
        // 方式二 使用 update(QueryWrapper)，前提 lambdaUpdateWrapper中的 sqlSet不能为空 为空报错没有修改条件
        LambdaUpdateWrapper<Test> queryWrapper2 = Wrappers.lambdaUpdate(Test.builder().id(Objects.requireNonNull(test.getId())).build());
        queryWrapper2.setSql(StrUtil.isNotBlank(test.getQuerystring()), String.format("queryString = '%s'", test.getQuerystring()));
//        testService.update(queryWrapper2);

        // 方式三 使用update(Entity, QueryWrapper)，前者实体为修改后的数据，后者为修改条件
        LambdaQueryWrapper<Test> queryWrapper3 = Wrappers.lambdaQuery(Test.builder().id(Objects.requireNonNull(test.getId())).build());
//        testService.update(test, queryWrapper3);

        // 方式四： 使用updateById(Id) 根据id进行修改 实体类的其他值 进行同步更新
        return testService.updateById(test) ? Result.success("操作成功~") : Result.failed("操作失败！");
    }

    @CacheEvict(key = "#id")
    @DeleteMapping("/{id}")
    public Result deleteTest(@PathVariable Long id) {
        // 这里 mybatis-plus 对 删除方法 注释的很清楚了 就不做演示了
        return testService.removeById(id) ? Result.success("操作成功~") : Result.failed("操作失败！");
    }


    @Cacheable(key = "#id", unless = "#result.data == null")
    @GetMapping("{id}")
    public Result findTestById(@PathVariable Long id) {
        // lambdaQuery 查询 可以直接查询 one、list、count、page 基本都有
        Test test1 = testService.lambdaQuery().eq(Objects.nonNull(id), Test::getId, id).one();
        log.info("test1 => {}", test1);
        // 直接通过 主键id 查询
        Test test2 = testService.getById(id);
        log.info("test2 => {}", test2);
        // baseMapper 查询
        Test test3 = testService.getBaseMapper().selectById(id);
        log.info("test3 => {}", test3);
        // 方法很多 这边就不一一 演示了
        return Result.success("查询成功~", test3);
    }
}
