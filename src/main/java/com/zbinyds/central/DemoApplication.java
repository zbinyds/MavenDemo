package com.zbinyds.central;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Package: com.zbinyds.central
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/4/9 18:32
 */

@SpringBootApplication
@MapperScan("com.zbinyds.central")
@EnableCaching
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
