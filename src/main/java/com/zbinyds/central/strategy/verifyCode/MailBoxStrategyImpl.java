package com.zbinyds.central.strategy.verifyCode;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:58
 */

@Component
public class MailBoxStrategyImpl implements VerifyCodeStrategy {
    @Override
    public String exec(HttpServletResponse response) {
        return "3";
    }

    @Override
    public VerifyCodeEnum getInstance() {
        return VerifyCodeEnum.MAILBOX;
    }
}
