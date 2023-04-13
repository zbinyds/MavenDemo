package com.zbinyds.central.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Package: com.zbinyds.central.pojo
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/4/13 22:56
 */

@Data
public class Test implements Serializable {

    private Long id;

    private String queryString;

    private Date createTime;

    private Date updateTime;
}
