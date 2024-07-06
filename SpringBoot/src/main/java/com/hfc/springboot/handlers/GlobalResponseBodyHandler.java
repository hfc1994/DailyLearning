package com.hfc.springboot.handlers;

import com.hfc.springboot.model.CommonResult;
import com.hfc.springboot.model.ExceptionEnum;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by hfc on 2024/6/30.
 *
 * ResponseBodyAdvice 接口是在 Controller 执行 return 之后，在 response 返回给客户端之前，
 * 执行的对 response 的一些处理
 *
 * 请求  --->   HttpInputMessage  --->                         --->
 *                                       HttpMessageConverter        Spring MVC
 * 响应  <---   HttpOutMessage    <---                         <---
 */
@RestControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // controller 返回 void 的时候会走这个逻辑
        if (body == null) {
            return CommonResult.error(ExceptionEnum.NO_DATA_FOUND);
        }

        if (body instanceof CommonResult) {
            return body;
        }
        return CommonResult.success(body);
    }

}
