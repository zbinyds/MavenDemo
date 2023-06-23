package com.zbinyds.central;

import com.zbinyds.central.functionalInterface.TestInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Package: com.zbinyds
 * @Author zbinyds@126.com
 * @Description:
 * @Create 2023/4/16 17:48
 */

@SpringBootTest
@Slf4j
public class TestClass {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RedisTemplate redisTemplate;

//    @Test
//    public void test1() {
//        String sql = "select * from t_test where id = ?";
//        List<com.zbinyds.central.pojo.Test> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<com.zbinyds.central.pojo.Test>(com.zbinyds.central.pojo.Test.class), 1);
//        System.out.println(list);
//    }

    @Test
    public void test2() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("占领王杰领地");
            }
        }).start();
    }

    @Test
    public void test3(){
        redisTemplate.convertAndSend("zbinyds1", "hello, world!");
    }

    @Test
    public void test4() {
        List<String> list1 = Arrays.asList("name", "phone");
        List<Object> list2 = Arrays.asList("123", 123);

        Stream<Object> stream = Stream.concat(list1.stream(), list2.stream());
        List<Object> list = stream.collect(Collectors.toList());
        log.info("list => {}", list);
    }

    @Test
    public void test5() {
        com.zbinyds.central.pojo.Test test = com.zbinyds.central.pojo.Test.builder().querystring("zbin").build();
        TestInterface<com.zbinyds.central.pojo.Test> testInterface = entity -> Objects.isNull(entity.getId());
        log.info("boolean => {}", testInterface.test(test));
        log.info("========================分割线=========================");
    }

    @Test
    public void test6() {
        // todo
    }
}
