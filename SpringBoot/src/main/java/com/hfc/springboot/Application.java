package com.hfc.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * ServletComponentScan用于扫描WebFilter注解
 *
 * 可用@MapperScan来取代具体类上面的@Mapper
 */
//@MapperScan(basePackages = "com.hfc.springboot.mapper")
@ServletComponentScan(basePackages = "com.hfc.springboot.filters")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
