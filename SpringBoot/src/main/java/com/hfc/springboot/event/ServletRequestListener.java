package com.hfc.springboot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * Created by hfc on 2020/7/10.
 *
 * 对request事件的侦听器
 */
@Component
public class ServletRequestListener
        implements ApplicationListener<ServletRequestHandledEvent> {

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        System.out.println("--- request event ---");
        System.out.println(event.getRequestUrl());
        System.out.println(event.getClientAddress());
        System.out.println(event.getMethod());
        System.out.println(event.getServletName());
        System.out.println(event.getStatusCode());
        System.out.println("---   event end   ---");
        System.out.println();
    }
}
