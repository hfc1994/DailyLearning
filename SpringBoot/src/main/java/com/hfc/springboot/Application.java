package com.hfc.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ServletComponentScan用于扫描WebFilter注解
 *
 * 可用@MapperScan来取代具体类上面的@Mapper
 */
@EnableAspectJAutoProxy
// 不添加MapperScan也能正常查询，添加后会影响到@WebMvcTest注解扫描到Mapper
// @MapperScan(basePackages = "com.hfc.springboot.mapper")
@ServletComponentScan(basePackages = "com.hfc.springboot.filters")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);

        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }

}
