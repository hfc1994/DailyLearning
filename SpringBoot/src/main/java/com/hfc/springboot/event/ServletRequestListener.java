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
        String msg = String.format("--- request event [url: %s, clint ip: %s, method: %s, servlet: %s, status code: %d]",
                event.getRequestUrl(), event.getClientAddress(),
                event.getMethod(), event.getServletName(), event.getStatusCode());
        System.out.println(msg);
    }
}
