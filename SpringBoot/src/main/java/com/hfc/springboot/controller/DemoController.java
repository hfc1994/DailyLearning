package com.hfc.springboot.controller;

import com.hfc.springboot.event.MockInvokeEvent;
import com.hfc.springboot.utils.ApplicationContextHolder;
import com.hfc.springboot.utils.EventPublishHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hfc on 2020/4/29.
 */
@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    private ApplicationContextHolder ach;

    @Autowired
    private EventPublishHolder publisher;

    @GetMapping("/event")
    public String testEvent() {
        String ret = "--- begin test event ---";
        publisher.getPublisher().publishEvent(new MockInvokeEvent("invoke testEvent method"));
        return ret;
    }

    @RequestMapping("/holder")
    public void testHolder() {
        ApplicationContext ac = ach.getAc();
        System.out.println(ac);
    }
}
