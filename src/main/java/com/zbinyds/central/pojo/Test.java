package com.zbinyds.central.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 测试表
 * @TableName t_test
 */
@TableName(value ="t_test")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test implements Serializable {

    private static final long serialVersionUID = 5283979627664961592L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 前端传递而来的查询字符串
     */
    @TableField("queryString")
    private String querystring;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}