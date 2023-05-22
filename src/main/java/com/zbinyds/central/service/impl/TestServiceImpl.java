package com.zbinyds.central.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbinyds.central.mapper.TestMapper;
import com.zbinyds.central.pojo.Test;
import com.zbinyds.central.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
* @author zbinyds
* @description 针对表【t_test(测试表)】的数据库操作Service实现
* @createDate 2023-05-20 20:00:47
*/

@Service
@RequiredArgsConstructor
public class TestServiceImpl extends ServiceImpl<TestMapper, Test>
    implements TestService{

    private final TestMapper testMapper;

    @Override
    public Page<Test> pageByQueryString(Integer pageNum, Integer pageSize, String queryString) {
        // 分页
        Page<Test> page = new Page<>(pageNum, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Test> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Test::getQuerystring, queryString);
        return testMapper.selectPage(page, queryWrapper);
    }
}




