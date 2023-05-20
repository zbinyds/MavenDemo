package com.zbinyds.central.mapper;

import com.zbinyds.central.pojo.InterfaceRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author zbinyds
 * @description 针对表【t_interface_records】的数据库操作Mapper
 * @createDate 2023-05-20 20:00:47
 * @Entity com.zbinyds.central.pojo.InterfaceRecords
 */
@Mapper
public interface InterfaceRecordsMapper extends BaseMapper<InterfaceRecords> {

}




