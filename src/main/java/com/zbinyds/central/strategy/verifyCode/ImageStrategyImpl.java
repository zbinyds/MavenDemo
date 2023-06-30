package com.zbinyds.central.strategy.verifyCode;

import cn.hutool.core.lang.UUID;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * @Package: com.zbinyds.central.strategy
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/6/12 20:54
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageStrategyImpl implements VerifyCodeStrategy {

    public static final String KAPTCHA_UUID_KEY_PREFIX = "kaptchaUUID::";

    private final RedisTemplate<String, Object> redisTemplate;

    private final DefaultKaptcha captchaProducer;

    @Override
    public String exec(HttpServletResponse response) {
        // 图片验证码 业务逻辑
        /*response.setHeader("Cache-Control", "no-store");
        response.setContentType("image/jpeg");*/
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String base64 = null;
        try {
            // 生成 文字验证码
            String code = captchaProducer.createText();
            // 生成 图片验证码
            BufferedImage image = captchaProducer.createImage(code);
            // 生成 UUID 凭证
            String uuid = UUID.fastUUID().toString();
            // 保存验证码到 redis, 验证码有效期 60 s
            redisTemplate.opsForValue().set(KAPTCHA_UUID_KEY_PREFIX + uuid, code, 60, TimeUnit.SECONDS);
            /* // 1、通过 outputStream 返回 图形验证码
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            IOUtils.closeQuietly(outputStream);*/
            // 2、返回base64编码二维码图片
            // 将 image 写入 outputStream 流中
            ImageIO.write(image, "jpg", outputStream);
            // base64编码
            base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            // 将 验证码凭证 uuid 写入 cookie 中, 有效期 60s
            Cookie cookie = new Cookie("kaptchaUUID", uuid);
            cookie.setMaxAge(60);
            response.addCookie(cookie);
            return base64;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return base64;
    }

    @Override
    public VerifyCodeEnum getInstance() {
        return VerifyCodeEnum.IMAGE;
    }

}
