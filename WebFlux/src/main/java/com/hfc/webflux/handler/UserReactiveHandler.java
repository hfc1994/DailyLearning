package com.hfc.webflux.handler;

import com.hfc.webflux.entity.User;
import lombok.Getter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hfc on 2023/6/1.
 */
@Component
public class UserReactiveHandler {

    @Getter
    private List<User> allUser;

    @PostConstruct
    public void init() {
        allUser = Arrays.asList(new User("zhangsan", 18), new User("lisi", 19),
                new User("wangwu", 20));
        allUser = new ArrayList<>(allUser);
    }

    @NonNull
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.fromStream(this.getAllUser().stream()), User.class);
    }

    @NonNull
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);

        return user.flatMap(dto -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.create(sink -> {
                    allUser.add(dto);
                    sink.success(dto);
                }), User.class));
    }

    @NonNull
    public Mono<ServerResponse> deleteUserByName(ServerRequest request) {
        String name = request.pathVariable("name");

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.create(sink -> {
                    boolean success = false;
                    for (User user : allUser) {
                        if (user.getName().equals(name)) {
                            success = true;
                            allUser.remove(user);
                            break;
                        }
                    }
                    sink.success(success);
                }), Boolean.class);
    }

}
