package com.hfc.springcloud.nacosconfig.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by hfc on 2021/4/28.
 * <p>
 * 在SpringCloud中，@ConfigurationProperties和@Value都能从nacos中获取值，两者有其一就行
 */
@RefreshScope
@Configuration
//@ConfigurationProperties(prefix = "person")
public class Person {

    @Value("${person.age:18}")
    private volatile int age;

    @Value("${person.name:wangwu}")
    private volatile String name;

    @PostConstruct
    public void printValue() {
        System.out.println("--- post construct ---");
        System.out.println(name + ":" + age);
        System.out.println("--- post construct ---");
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
