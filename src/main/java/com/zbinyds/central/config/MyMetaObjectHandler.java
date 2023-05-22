package com.zbinyds.central.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * mybatis-plus 字段自动填充配置
 * <p>
 * 踩坑记录:
 * 1、官方提供的配置方式有3种，但是需要保证 填充的类型 必须和 字段类型 一致，否则会报错。xxx字段 can not be null !!!
 * 2、如果字段配置了 fill = FieldFill.INSERT_UPDATE，那么在 insertFill 和 updateFill方法中都需要配置。
 */

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 插入记录时，自动填充 createTime 和 updateTime
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 修改记录时，自动填充 updateTime
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }

}