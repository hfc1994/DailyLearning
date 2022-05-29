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

    /**
     * 方法里可用的参数可以查看 ExceptionHandler 的注释
     */
    @ExceptionHandler(ArithmeticException.class)
    public String globalArithmeticException(ArithmeticException e) {
        System.out.println("*** SomeArithmeticException ***");
        e.printStackTrace();
        System.out.println("******");
        return "SomeArithmeticException: " + e.getMessage();
    }

    @ExceptionHandler(NullPointerException.class)
    public String globalNullPointerException(RuntimeException e) {
        System.out.println("*** SomeNullPointerException ***");
        e.printStackTrace();
        System.out.println("******");
        return "SomeNullPointerException";
    }

    /**
     * 虽然 NullPointerException 是 RuntimeException 的子类
     * 但发生 NullPointerException 时只会调用 globalNullPointerException()
     * 没有 @ExceptionHandler(NullPointerException.class) 时才会调用 globalRuntimeException(RuntimeException e)
     */
    @ExceptionHandler(RuntimeException.class)
    public String globalRuntimeException(RuntimeException e) {
        System.out.println("*** SomeRuntimeException ***");
        e.printStackTrace();
        System.out.println("******");
        return "SomeRuntimeException: " + e.getMessage();
    }
}
