package com.hfc.springcloud.nacosconfig.listener;

import com.alibaba.nacos.api.config.listener.AbstractSharedListener;
import com.hfc.springcloud.nacosconfig.config.Location;
import com.hfc.springcloud.nacosconfig.config.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2021/4/28.
 */
@Component
public class NacosListener implements SmartApplicationListener {

    @Autowired
    private Person person;

    @Autowired
    private Location location;

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return RefreshEvent.class.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        RefreshEvent event = (RefreshEvent) applicationEvent;
        System.out.println("----------------");
        System.out.println(event.getEventDesc());
        Object o = event.getSource();
        System.out.println(o.getClass().getName());
        if (AbstractSharedListener.class.isAssignableFrom(o.getClass())) {
            // 此时数据还没更新
            System.out.println("person: " + person.getAge());
            System.out.println("location: " + location.getNumber());
        }
        System.out.println("----------------");
    }

    @EventListener
    public void configChanged(EnvironmentChangeEvent event) {
        System.out.println("+++++++");
        System.out.println(event.getKeys());
        // 此时数据还没更新
        System.out.println("person: " + person.getAge());
        System.out.println("location: " + location.getNumber());
        System.out.println("+++++++");
    }

}
