package com.hfc.springcloud.nacosconfig.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by hfc on 2021/4/29.
 */
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "location")
public class Location {

    private String name;

    private int number;

    @PostConstruct
    public void printValue() {
        System.out.println("*** post construct ***");
        System.out.println(name + ":" + number);
        System.out.println("*** post construct ***");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
