package com.hfc.springboot.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

/**
 * Created by hfc on 2024/6/30.
 */
public class CommonResult<T> {

    public static final Integer SUCCESS_CODE = 0;

    @Getter
    private Integer code;

    @Getter
    private String message;

    @Getter
    private T data;

    public static <T> CommonResult<T> error() {
        return error(ExceptionEnum.COMMON_EXCEPTION);
    }

    public static <T> CommonResult<T> error(ExceptionEnum exception) {
        return error(exception.getCode(), exception.getMessage());
    }

    public static <T> CommonResult<T> error(Integer code, String msg) {
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.message = msg;
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = SUCCESS_CODE;
        result.data = data;
        return result;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(code);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isError() {
        return !isSuccess();
    }

}
