package com.zbinyds.central.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zbinyds.central.pojo.Test;

/**
* @author zbinyds
* @description 针对表【t_test(测试表)】的数据库操作Service
* @createDate 2023-05-20 20:00:47
*/
public interface TestService extends IService<Test> {

    Page<Test> pageByQueryString(Integer pageNum, Integer pageSize, String queryString);
}
