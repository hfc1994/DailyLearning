package com.hfc.springboot.handlers;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
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
     * 发生请求没有匹配到控制器的情况下，即 404 时触发，前提是配置了如下参数
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.web.resources.add-mappings=false
     *
     * 同时存在实现了 ErrorController 的子控制器的情况下，优先触发该全局异常处理器
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String globalNoHandlerFoundException(NoHandlerFoundException e) {
        System.out.println("*** NoHandlerFoundException ***");
        return "NoHandlerFoundException: " + e.getMessage();
    }

    /**
     * 进入 Controller 前的异常：
     */
    @ExceptionHandler({
            // NoHandlerFoundException.class                    // 根据请求 url 没有查找到对应的控制器，也即是 404
            HttpRequestMethodNotSupportedException.class,       // 能匹配到控制器，但没有对应的 http 方法（GET、POST等）
            HttpMediaTypeNotSupportedException.class,           // 比如请求头不匹配
            MissingPathVariableException.class,                 // 缺失路径参数
            MissingServletRequestParameterException.class,      // 缺少请求参数
            TypeMismatchException.class,                        // 参数类型匹配失败
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            BindException.class,                                // 参数绑定异常
            MethodArgumentNotValidException.class,              // 参数校验异常
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })
    public String othersException(Exception e) {
        return e.getMessage();
    }

}
