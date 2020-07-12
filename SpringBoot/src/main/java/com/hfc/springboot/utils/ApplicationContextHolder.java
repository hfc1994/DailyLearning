package com.hfc.springboot.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2020/4/29.
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private ApplicationContext ac;

    public ApplicationContext getAc() {
        return ac;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("--- invoke setApplicationContext ---");
        this.ac = applicationContext;
    }
}
