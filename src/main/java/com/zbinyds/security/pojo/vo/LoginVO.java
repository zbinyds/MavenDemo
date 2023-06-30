package com.zbinyds.security.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Package: com.zbinyds.security.pojo.vo
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/26 18:41
 */

@Data
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 424797071649782370L;

    /**
     * 用户名
     */
    @NotNull
    @NotBlank(message = "用户名不能为空！")
    private String username;

    /**
     * 密码
     */
    @NotNull
    @NotBlank(message = "密码不能为空！")
    private String password;

    /**
     * 验证码
     */
//    @NotNull
//    @NotBlank(message = "验证码不能为空！")
//    @Size(min = 6, max = 6, message = "验证码长度不合法！")
    private String verifyCode;
}
