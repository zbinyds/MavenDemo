package com.zbinyds.security.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Kaptcha 配置
 *
 * @Author zbinyds
 */
@Configuration
public class CaptchaConfig {

    @Bean("captchaProducer")
    public DefaultKaptcha producer() {
        // 所有可选配置项 存放在 com.google.code.kaptcha.Constants 类中
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.border.color", "105,179,90");
        properties.put("kaptcha.textproducer.font.color", "72,118,255");
        properties.put("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.WaterRipple");
        properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.DefaultNoise");
        properties.put("kaptcha.noise.color", "72,118,255");
        properties.put("kaptcha.image.width", "125");
        properties.put("kaptcha.image.height", "50");
//        properties.put("kaptcha.textproducer.font.size", "20");
        // 设置验证码长度
        properties.put("kaptcha.textproducer.char.length", "6");
        properties.put("kaptcha.textproducer.char.font.names", "Arial, Courier");
//        properties.put("kaptcha.textproducer.char.space", "6");
        properties.put("kaptcha.textproducer.impl", "com.google.code.kaptcha.text.impl.DefaultTextCreator");
//        properties.put("kaptcha.textproducer.char.string","123"); // 指定随机字符串范围
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
