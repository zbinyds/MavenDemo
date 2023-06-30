package com.zbinyds.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbinyds.security.pojo.User;
import com.zbinyds.security.pojo.vo.LoginVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zbinyds
 * @description 针对表【t_user(用户表)】的数据库操作Service
 * @createDate 2023-06-26 18:37:09
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param loginVo 入参
     * @return token
     */
    String login(LoginVO loginVo, String kaptchaUUID);

    /**
     * 生成图片验证码
     *
     * @param response 响应体
     * @return base64后 图片验证码
     */
    String createVerifyCode(Integer type, String name, HttpServletResponse response);
}
