package com.zbinyds.security.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbinyds.central.handler.GlobalException;
import com.zbinyds.central.strategy.verifyCode.ImageStrategyImpl;
import com.zbinyds.central.strategy.verifyCode.VerifyCodeEnum;
import com.zbinyds.central.strategy.verifyCode.VerifyCodeStrategy;
import com.zbinyds.central.strategy.verifyCode.VerifyCodeStrategyFactory;
import com.zbinyds.security.mapper.UserMapper;
import com.zbinyds.security.pojo.SecurityUser;
import com.zbinyds.security.pojo.User;
import com.zbinyds.security.pojo.vo.LoginVO;
import com.zbinyds.security.service.UserService;
import com.zbinyds.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * @author zbinyds
 * @description 针对表【t_user(用户表)】的数据库操作Service实现
 * @createDate 2023-06-26 18:37:09
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy}) // 依赖延迟注入
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService, UserDetailsService {

    private final VerifyCodeStrategyFactory verifyCodeStrategyFactory;

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.lambdaQuery().eq(User::getUsername, username).one();
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        return new SecurityUser(user);
    }

    @Override
    public String login(LoginVO loginVo, String kaptchaUUID) {
        // 验证码校验
        String realCode = (String) redisTemplate.opsForValue().get(ImageStrategyImpl.KAPTCHA_UUID_KEY_PREFIX + kaptchaUUID);
        if (!StringUtils.hasLength(realCode) || !loginVo.getVerifyCode().equals(realCode)) {
            // 验证码不存在或错误
            throw new GlobalException("验证码不存在或错误!");
        }

        // 通过 springSecurity 进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 用户信息
        User user = ((SecurityUser) authentication.getPrincipal()).getCurrentUser();
        Map<String, Object> claims = BeanUtil.beanToMap(user);
        // 生成token
        return JwtUtil.createJWT(claims);
    }

    @Override
    public String createVerifyCode(Integer type, String name, HttpServletResponse response) {
        // 1、使用前端传递而来的type字段, 获取枚举常量;
        VerifyCodeEnum verifyCodeEnum = VerifyCodeEnum.getStrategy(type);

        // 2、根据枚举常量, 获取相应的执行策略
        VerifyCodeStrategy strategy = verifyCodeStrategyFactory.createStrategy(verifyCodeEnum);

        // 3、执行策略, 获取执行结果
        return strategy.exec(response);
    }
}




