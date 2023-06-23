package com.zbinyds.central;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Package: com.zbinyds.central
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/4/9 18:32
 */

@Slf4j
@SpringBootApplication(scanBasePackages = {"com.zbinyds"})
@EnableTransactionManagement
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        log.info("JavaApp Run Start!!!");
    }
}
