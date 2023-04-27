package com.hfc.springboot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

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

    /**
     * 在配置文件中添加一个配置server.error.path=/errorNew，使其指向当前的Controller
     */
    @RequestMapping(path = "/errorNew")
    public String errorPathNew() {
        return "it's something wrong happening from New";
    }

    /**
     * 在较新的 springboot 版本中，该函数已经被标记为过时了
     */
//    @Override
//    public String getErrorPath() {
//        return error_path;
//    }

//    /**
//     * 在新版中可以添加这么两个方法，一个处理 JSON 的错误接口，一个处理 html 的页面错误请求
//     * 同时在配置文件中添加一个配置 server.error.path=/error，使其指向当前的 Controller
//     */
//    @RequestMapping(produces = {"application/json"})
//    public String handlerError(HttpServletRequest request, HttpServletResponse response){
//        return "it's something wrong happening, with code = " + response.getStatus();
//    }
//
//    @RequestMapping
//    public ModelAndView handlerPageError(){
//        return new ModelAndView("/index.html", new HashMap<>());
//    }
}
