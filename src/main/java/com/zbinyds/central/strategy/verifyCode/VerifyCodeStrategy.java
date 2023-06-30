package com.zbinyds.central.strategy.verifyCode;

import javax.servlet.http.HttpServletResponse;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:48
 */
public interface VerifyCodeStrategy {

    String exec(HttpServletResponse response);

    VerifyCodeEnum getInstance();
}
