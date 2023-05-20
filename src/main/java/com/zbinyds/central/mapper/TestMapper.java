package com.zbinyds.central.mapper;

import com.zbinyds.central.pojo.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
* @author zbinyds
* @description 针对表【t_test(测试表)】的数据库操作Mapper
* @createDate 2023-05-20 20:00:47
* @Entity com.zbinyds.central.pojo.Test
*/

@Mapper
public interface TestMapper extends BaseMapper<Test> {

}




