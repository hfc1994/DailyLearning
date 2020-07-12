package com.hfc.springboot.event;

import org.springframework.context.ApplicationEvent;

/**
 * Created by hfc on 2020/7/10.
 *
 * 创建的自定义事件
 * 模拟触发的事件
 */
public class MockInvokeEvent extends ApplicationEvent {

    private String content;

    public MockInvokeEvent(String content) {
        super(content);
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
