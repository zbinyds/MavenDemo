package com.zbinyds.central.pojo.dto;

import lombok.*;

import java.io.Serializable;

/**
 * @Package: com.zbinyds.central.pojo
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/5/20 11:36
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeyGeneratorDTO implements Serializable {

    private static final long serialVersionUID = -3582963231215015321L;

    /**
     * 接口参数名称
     */
    private String paramName;

    /**
     * 接口参数值
     */
    private String paramValue;

    /**
     * 参数索引
     */
    private Integer index;
}
