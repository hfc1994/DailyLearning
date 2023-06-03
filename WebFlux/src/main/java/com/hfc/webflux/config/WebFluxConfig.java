package com.hfc.webflux.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.io.File;

/**
 * Created by hfc on 2023/6/2.
 */
@Configuration
@EnableWebFlux
public class WebFluxConfig implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + System.getProperty("user.dir")
                        + File.separator + "file" + File.separator);
    }

}
