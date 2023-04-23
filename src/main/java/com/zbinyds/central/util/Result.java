package com.zbinyds.central.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @Package: com.zbinyds.central.util
 * @Author zbinyds@126.com
 * @Description: 通用接口返回类 Result
 * @Create 2023/4/23 13:21
 */

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 6072554622632623513L;

    /**
     * 请求接口是否成功
     */
    private Boolean success;

    /**
     * 接口返回的提示信息
     */
    private String msg;

    /**
     * 接口携带的返回值
     */
    private T data;

    private Result(Boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(true, msg, data);
    }

    public static <T> Result<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> Result<T> success() {
        return success(null, null);
    }

    public static <T> Result<T> failed(String msg, T data) {
        return new Result<>(false, msg, data);
    }

    public static <T> Result<T> failed(String msg) {
        return failed(msg, null);
    }

    public static <T> Result<T> failed() {
        return failed(null, null);
    }

    public Result<T> of(T data){
        this.setData(data);
        return this;
    }
}
