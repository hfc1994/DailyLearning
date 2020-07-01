package com.hfc.springboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by hfc on 2019/10/10.
 *
 * ConfigurationProperties需要spring-boot-configuration-processor依赖
 */
@Component
@ConfigurationProperties(prefix = "mail")
public class UserMailProperties {

    private String from;

    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
