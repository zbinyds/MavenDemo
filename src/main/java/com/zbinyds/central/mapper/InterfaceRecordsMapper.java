package com.zbinyds.central.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbinyds.central.pojo.InterfaceRecords;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zbinyds
 * @description 针对表【t_interface_records】的数据库操作Mapper
 * @createDate 2023-05-20 20:00:47
 * @Entity com.zbinyds.central.pojo.InterfaceRecords
 */
@Mapper
public interface InterfaceRecordsMapper extends BaseMapper<InterfaceRecords> {

    /**
     * @param ids 如果不显示使用 Param注解 默认 字段名为 list
     * @return
     * @MapKey 将返回的结果转成 map, value 为根据某个字段 toMap
     */
    @MapKey("id")
    Map<Long, InterfaceRecords> asyncSelectByIds(@Param("ids") List<Long> ids);
}




