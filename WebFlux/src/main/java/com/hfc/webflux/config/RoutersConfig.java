package com.hfc.webflux.config;

import com.hfc.webflux.handler.PersonReactiveHandler;
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
    RouterFunction<ServerResponse> userRoutes(UserReactiveHandler handler) {
        return RouterFunctions.route(GET("/v1/rx/user/all"),
                        handler::getAllUser)
                .andRoute(POST("/v1/rx/user")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::createUser)
                .andRoute(DELETE("/v1/rx/user/{name}")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::deleteUserByName);
    }

    @Bean
    RouterFunction<ServerResponse> personRoutes(PersonReactiveHandler handler) {
        return RouterFunctions.route(GET("/v1/rx/person/{id}")
                                .and(accept(MediaType.APPLICATION_JSON)), handler::getPersonById)
                .andRoute(GET("/v1/rx/person/{id}/exist")
                        .and(accept(MediaType.APPLICATION_JSON)), handler::existedPersonByid);
    }

}
