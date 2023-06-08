package com.zbinyds.central.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Package: com.zbinyds.central.handle
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/7 21:33
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException{
    private static final long serialVersionUID = 1871624718862603951L;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常状态码
     */
    private Integer code;

    public GlobalException(String message) {
        this.message = message;
    }
}
