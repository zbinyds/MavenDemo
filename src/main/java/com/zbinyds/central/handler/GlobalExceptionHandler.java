package com.zbinyds.central.handler;

import com.zbinyds.central.util.Result;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.Objects;

/**
 * @Package: com.zbinyds.central.handler
 * @Author zbinyds@126.com
 * @Description: 全局异常处理器
 * @Create 2023/6/7 21:30
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常捕获
     * @param exception 异常
     * @return 异常信息
     */
    @ExceptionHandler(GlobalException.class)
    public Result error(GlobalException exception){
        return Result.failed(exception.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public Result error(BindException exception){
        return Result.failed(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public Result error(ValidationException exception){
        return Result.failed(exception.getLocalizedMessage());
    }
}