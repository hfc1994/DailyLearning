package com.hfc.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Created by hfc on 2023/5/16.
 */
@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "com.hfc.webflux.repository")
public class WebFluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxApplication.class, args);
    }

}
