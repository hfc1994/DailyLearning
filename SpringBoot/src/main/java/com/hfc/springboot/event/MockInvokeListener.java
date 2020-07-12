package com.hfc.springboot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2020/7/10.
 *
 * 创建的对自定义事件的侦听器
 */
@Component
public class MockInvokeListener implements ApplicationListener<MockInvokeEvent> {

    @Override
    public void onApplicationEvent(MockInvokeEvent event) {
        System.out.println("--- mock invoke event ---");
        System.out.println(event.getContent());
        System.out.println();
    }

    /**
     * 通过注解也能实现监听程序事件
     * 这种方式不需要实现ApplicationListener接口
     * 方法的入参类似就是其关注的事件
     * @param event 侦听器关注的事件类型
     */
    @EventListener
    public void eventCaptured(MockInvokeEvent event) {
        System.out.println("--- event captured by annotation ---");
        System.out.println(event.getContent());
        System.out.println();
    }
}
