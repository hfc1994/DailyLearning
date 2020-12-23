package com.hfc.springboot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hfc on 2020/12/23.
 *
 * SpringBoot默认的错误处理类是BasicErrorController
 * 当我们自己实现了ErrorController接口，那么就会使用我们自己的实现类
 * 当系统出现异常并且没有被正确处理时就会触发这个控制器
 * 如果有ControllerAdvice或是RestControllerAdvice定义并捕获部分未知异常，那么优先使用advice里面的handler
 *
 * 像是403或是404的错误只会被这里处理
 */
@RestController
public class MyErrorController implements ErrorController {

    private final static String error_path = "/error";

    @RequestMapping(path = error_path)
    public String errorPath() {
        return "it's something wrong happening";
    }

    @Override
    public String getErrorPath() {
        return error_path;
    }
}
