package com.zbinyds.security.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Package: com.zbinyds.security.util
 * @Author zbinyds@126.com
 * @Description: 响应工具类
 * @Create 2023/6/29 22:00
 */
public class ResponseUtil {

    private static final Logger log = LoggerFactory.getLogger(ResponseUtil.class);

    public static void out(HttpServletRequest request, HttpServletResponse response, Object result) {
        try {
            // 对象转json
            String json = JSON.toJSONString(result);
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().print(json);
        } catch (IOException e) {
            log.error("Error Message: {}", e.getMessage());
        }
    }
}
