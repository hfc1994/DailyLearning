package com.hfc.springboot.utils;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2020/7/10.
 *
 * 获取事件发布器
 */
@Component
public class EventPublishHolder implements ApplicationEventPublisherAware {

    public ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public ApplicationEventPublisher getPublisher() {
        return publisher;
    }
}
