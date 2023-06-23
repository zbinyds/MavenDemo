package com.zbinyds.central.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbinyds.central.pojo.InterfaceRecords;

import java.util.List;

/**
 * @author zbinyds
 * @description 针对表【t_interface_records】的数据库操作Service
 * @createDate 2023-05-20 20:00:47
 */
public interface InterfaceRecordsService extends IService<InterfaceRecords> {

    void insertEntity(InterfaceRecords entity) throws InterruptedException;

    List<InterfaceRecords> asyncSelectByIds(List<Long> ids);
}
