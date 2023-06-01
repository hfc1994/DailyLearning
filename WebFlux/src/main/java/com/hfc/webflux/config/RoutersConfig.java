package com.hfc.webflux.config;

import com.hfc.webflux.handler.UserReactiveHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * Created by hfc on 2023/6/1.
 */
@Configuration
public class RoutersConfig {

    @Bean
    RouterFunction<ServerResponse> routes(UserReactiveHandler handler) {
        return RouterFunctions.route(GET("/allUser"), handler::getAllUser)
                .andRoute(POST("/user")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::createUser)
                .andRoute(DELETE("/user/{name}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::deleteUserByName);
    }

}
