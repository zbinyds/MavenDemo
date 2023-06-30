package com.zbinyds.security.handler;

import com.zbinyds.central.util.Result;
import com.zbinyds.security.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Package: com.zbinyds.security.handler
 * @Author zbinyds@126.com
 * @Description: 未授权配置
 * @Create 2023/6/29 22:42
 */

@Component
public class AccessRejectHandler implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(AccessRejectHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("AccessDeniedException error: {}", accessDeniedException.getMessage());
        ResponseUtil.out(request, response, Result.failed("没有权限访问!", 403));
    }
}
