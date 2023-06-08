package com.zbinyds.central.controller;

import com.zbinyds.central.pojo.vo.ValidatedVO;
import com.zbinyds.central.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * @Package: com.zbinyds.central.controller
 * @Author zbinyds@126.com
 * @Description: Validated校验controller
 * @Create 2023/6/7 21:10
 *
 * 备注说明:
 * 1、入参如果是类对象, 需要在类前加上 @Valid 或者 @Validated注解, 然后在实体类对应的字段前 加上校验注解;
 * 2、入参如果是单个参数, 在controller层上 加上@Validated注解, 然后在对应的入参前 价格校验注解;
 * 3、如果希望在service层用到校验注解, 用法和第二点一致, 但是 实在service接口里加校验注解, 而不是实现类里加;
 */

@RestController
@RequestMapping("/validated")
@Slf4j
@Validated
public class ValidatedController {

    /**
     * post请求校验 => RequestBody
     *
     * @param vo 请求实体对象
     * @return result
     */
    @PostMapping("/valid1")
    public Result valid1(@RequestBody @Valid ValidatedVO vo) {
        log.info("success => {}", vo);
        return Result.success("请求成功!~").of(vo);
    }

    /**
     * post请求校验 => RequestParam
     *
     * @param str 字符串
     * @param size integer
     * @return result
     */
    @PostMapping("/valid2")
    public Result valid2(
            @NotBlank(message = "必填参数不能为空!")
            @Size(min = 1, max = 5, message = "str长度范围[1,5]") String str,
            @NotNull(message = "必填参数不能为空!")
            @Range(min = 1, max = 10, message = "size范围[1,10]") Integer size
    ) {
        log.info("success => str:{} size:{}", str, size);
        return Result.success("请求成功!~");
    }

    /**
     * post请求校验 => PathVariable
     *
     * @param phone 手机号
     * @param strings 字符串列表
     * @return result
     */
    @PostMapping("/valid3/{phone}/{strings}")
    public Result valid3(
            @PathVariable
            @NotBlank(message = "必填参数不能为空!")
            @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号不合法!")
            String phone,
            @PathVariable
            @NotEmpty(message = "必填参数不能为空!")
            @Size(max = 2, min = 1, message = "strings 列表长度范围[1,2]")
            List<String> strings
    ) {
        log.info("success => str:{} size:{}", phone, strings);
        return Result.success("请求成功!~");
    }

    /**
     * get请求校验
     *
     * @param queryString 查询条件
     * @return result
     */
    @GetMapping("/valid4")
    public Result valid4(@NotBlank(message = "必填参数不能为空!") String queryString) {
        log.info("success => {}", queryString);
        return Result.success("请求成功!~", queryString);
    }
}
