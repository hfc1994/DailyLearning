package com.hfc.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by hfc on 2020/7/1.
 */
@EnableAspectJAutoProxy
@Configuration
public class AspectProxyConfig {

    @Bean
    public LogAspects logAspects() {
        return new LogAspects();
    }
}
