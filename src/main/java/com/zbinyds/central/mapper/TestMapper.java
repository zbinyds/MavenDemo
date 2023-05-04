package com.zbinyds.central.mapper;

import com.zbinyds.central.pojo.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    Test selectByPrimaryKey(Integer id);

    List<Test> selectAll();

    int updateByPrimaryKey(Test record);

    List<Test> findZbinList(String queryString);
}