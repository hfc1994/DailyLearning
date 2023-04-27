package com.hfc.springboot.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    /**
     * 发生请求没有匹配到控制器的情况下，即404时触发，前提是配置了如下参数
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.web.resources.add-mappings=false
     *
     * 同时存在实现了ErrorController的子控制器的情况下，优先触发该全局异常处理器
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String globalNoHandlerFoundException(NoHandlerFoundException e) {
        System.out.println("*** NoHandlerFoundException ***");
        return "NoHandlerFoundException: " + e.getMessage();
    }
}
