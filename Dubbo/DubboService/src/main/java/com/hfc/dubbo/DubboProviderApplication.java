package com.hfc.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.hfc.dubbo.provider")
// 可以用这种方式指定application.properties、
// 不过可能会有参数在比较早时期就被需要而导致启动失败，比如本例中的spring.main.allow-bean-definition-overriding
@PropertySource("classpath:provider.properties")
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }

}
