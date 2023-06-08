package com.zbinyds.central.pojo.vo;

import com.zbinyds.central.pojo.InterfaceRecords;
import com.zbinyds.central.pojo.Test;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Package: com.zbinyds.central.pojo.vo
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/7 21:14
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidatedVO implements Serializable {

    private static final long serialVersionUID = 7960944980564946112L;

    /**
     * 当前页码
     */
    @NotNull(message = "pageNum不能为空!")
    @Min(value = 1, message = "不能低于1!")
    @Max(value = 100, message = "不能超过100!")
    private Integer pageNum;

    /**
     * 每页显示的记录数
     */
    @NotNull(message = "pageSize不能为空!")
    @Max(value = 50, message = "每页显示的记录数不能超过50!")
    private Integer pageSize;

    /**
     * 实体类列表
     */
    @NotEmpty(message = "列表长度不能为空!")
    private List<Test> tests;

    /**
     * 实体类
     */
    @Valid
    @NotNull(message = "records不能为空!")
    private InterfaceRecords records;
}
