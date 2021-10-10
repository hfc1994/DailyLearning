package com.hfc.springboot.handlers;

import com.hfc.springboot.annotation.JwtToken;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by hfc on 2021/10/10.
 */
@Component
public class JwtTokenResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        String token;
        token = (String) webRequest.getAttribute("JwtToken", RequestAttributes.SCOPE_REQUEST);

        if (token == null) {
            token = webRequest.getHeader("JwtToken");
        }

        if (token == null) {
            // ?token=xxxxxx
            token = webRequest.getParameter("token");
        }

        if (token == null) {
            throw new RuntimeException("no token found");
        }
        return token;
    }

}
