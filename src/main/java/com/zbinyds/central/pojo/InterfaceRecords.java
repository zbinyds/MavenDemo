package com.zbinyds.central.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口调用记录表
 * @TableName t_interface_records
 */
@TableName(value ="t_interface_records")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceRecords implements Serializable {

    private static final long serialVersionUID = -2929053104584681309L;

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

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