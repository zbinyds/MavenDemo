package com.zbinyds.security.controller;

import com.zbinyds.central.annotation.FrequencyControl;
import com.zbinyds.central.util.Result;
import com.zbinyds.security.pojo.vo.LoginVO;
import com.zbinyds.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.zbinyds.security.controller
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/26 18:38
 */

@Slf4j
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody @Validated LoginVO loginVo, @CookieValue(value = "kaptchaUUID", required = false) String kaptchaUUID) {
        return Result.success("登录成功~", userService.login(loginVo, kaptchaUUID));
    }

    @FrequencyControl(value = 1, timeUnit = TimeUnit.MINUTES)
    @PostMapping("/verifyCode")
    public Result<String> createVerifyCode(Integer type, String name, HttpServletResponse response) {
        String base64 = userService.createVerifyCode(type, name, response);
        return Optional.ofNullable(base64).isPresent() ? Result.success("操作成功~", base64) : Result.failed("操作失败!");
    }
}
