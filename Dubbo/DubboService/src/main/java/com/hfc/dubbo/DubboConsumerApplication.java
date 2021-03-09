package com.hfc.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.hfc.dubbo.consumer")
// 可以用这种方式指定application.properties、
// 不过可能会有参数在比较早时期就被需要而导致启动失败，比如本例中的spring.main.allow-bean-definition-overriding
@PropertySource("classpath:consumer.properties")
public class DubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);
    }

}
