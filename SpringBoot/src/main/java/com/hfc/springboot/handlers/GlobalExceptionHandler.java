package com.hfc.springboot.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by hfc on 2020/12/23.
 *
 * 异常全局处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ArithmeticException.class)
    public String globalArithmeticException() {
        System.out.println("*** SomeArithmeticException ***");
        return "SomeArithmeticException";
    }

    @ExceptionHandler(NullPointerException.class)
    public String globalNullPointerException() {
        System.out.println("*** SomeNullPointerException ***");
        return "SomeNullPointerException";
    }
}
