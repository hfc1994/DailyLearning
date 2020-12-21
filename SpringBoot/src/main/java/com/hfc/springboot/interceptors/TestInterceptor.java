package com.hfc.springboot.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hfc on 2020/12/21.
 */
public class TestInterceptor implements HandlerInterceptor {

    private long begin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        begin = System.currentTimeMillis();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        long cost = System.currentTimeMillis() - begin;
        System.out.println("*** request " + request.getRequestURI() + " cost " + cost + " ms. ***");
    }
}
