package com.hfc.springboot.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by hfc on 2020/12/21.
 *
 * 有点类似以下结构，当 preHandle 返回 false 则 postHandle 和 afterCompletion 都不执行
 *
 * if (!preHandle()) {
 *     return;
 * }
 *
 * try {
 *     handler.execute();
 *     postHandle()
 * } catch (xxx) {
 * } finally {
 *     afterCompletion()
 * }
 *
 * 在 HandlerInterceptor 链上，先正序执行 preHandle，再倒序执行 postHandle，最后再倒序执行 afterCompletion
 *
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
        System.out.println("*** request post handle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long cost = System.currentTimeMillis() - begin;
        System.out.println("*** request " + request.getRequestURI() + " cost " + cost + " ms. ***");
    }
}
