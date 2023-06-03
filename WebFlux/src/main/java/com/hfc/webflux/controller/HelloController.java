package com.hfc.webflux.controller;

import com.hfc.webflux.entity.Book;
import com.hfc.webflux.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Created by hfc on 2023/5/16.
 *
 * 在 WebFlux 中，Mono 和 Flux 是非阻塞的写法，只有这样才能发挥 WebFlux 非阻塞+异步的特性
 * Mono：返回 0 或 1 个元素，即单个对象
 * Flux：返回 N 个元素，即 List 列表对象
 *
 */
@RestController
@RequestMapping("/v1/test")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello webflux";
    }

    @GetMapping("/user")
    public Mono<User> getUser() {
        return Mono.just(new User("zhangsan", 18));
    }

    @GetMapping("/user/default")
    public Mono<User> getDefaultUser() {
        return Mono.create(sink -> sink.success(new User("zhangsan", 18)));
    }

    @GetMapping("/book")
    public Flux<Book> getBooks() {
        Book b1 = new Book("三国演义", 1, 30.5D);
        Book b2 = new Book("三体", 2, 31D);
        return Flux.just(b1, b2);
    }

    public static void main(String[] args) {
        Flux.create(sink -> {
                    sink.next(Thread.currentThread().getName());
                    sink.complete();
                }).publishOn(Schedulers.single())
                .map(x ->  String.format("[%s] %s", Thread.currentThread().getName(), x))
                .publishOn(Schedulers.boundedElastic())
                .map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
                .subscribeOn(Schedulers.parallel())
                .toStream()
                .forEach(System.out::println);
    }

}
