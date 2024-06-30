package com.hfc.springboot.model;

import lombok.Getter;

/**
 * Created by hfc on 2024/6/30.
 */
public enum ExceptionEnum {

    SUCCESS(0, "success"),
    COMMON_EXCEPTION(1, "common exception"),
    NO_DATA_FOUND(2, "no data found"),
    RUNTIME_EXCEPTION(3, "runtime exception"),
    ;

    @Getter
    private final int code;

    @Getter
    private final String message;

    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

}
