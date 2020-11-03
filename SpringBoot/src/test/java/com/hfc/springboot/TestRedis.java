package com.hfc.springboot;

import com.hfc.springboot.services.AspectsRedisServices;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

// TODO: 2020/7/1 怎么写Spring Test
// TODO: 2020/7/1 RunWith的作用
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {

    @Autowired
    private AspectsRedisServices aspectsRedisServices;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedis() {
        List<String> keys = new ArrayList<>();
        for (int i=0; i<10; i++) {
            String key = "aaa" + i;
            aspectsRedisServices.insertStr(key, "how time fly" + i);
            System.out.println(aspectsRedisServices.getStr(key));
            keys.add(key);
        }
        System.out.println(aspectsRedisServices.getCountOfStr(keys));
        keys.remove(keys.size() - 1);
        System.out.println(aspectsRedisServices.getCountOfStr(keys));

        aspectsRedisServices.insertList("list", keys);
        System.out.println(aspectsRedisServices.getOneFromList("list"));
        System.out.println(aspectsRedisServices.getAllFromList("list"));
    }

    @Test
    public void testRedisLock() {

        Thread t1 = new Thread(() -> {
            Jedis jedis = new Jedis("localhost");
            System.out.println("lock-thread-1 begin gain lock");
            String ret = jedis.set("lock", "Thread1", "NX", "EX", 15);
            if ("OK".equals(ret)) {
                System.out.println("lock-thread-1 get lock");
            } else {
                System.out.println("lock-thread-1 fail to get lock");
            }
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock-thread-1 over");
        }, "lock-thread-1");

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Jedis jedis = new Jedis("localhost");
            System.out.println("lock-thread-2 begin gain lock");
            String ret = jedis.set("lock", "Thread2", "NX", "EX", 15);
            if ("OK".equals(ret)) {
                System.out.println("lock-thread-2 get lock");
            } else {
                System.out.println("lock-thread-2 fail to get lock");
            }
            try {
                TimeUnit.SECONDS.sleep(13);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock-thread-2 try to gain lock again");
            String ret2 = jedis.set("lock", "Thread2", "NX", "EX", 15);
            if ("OK".equals(ret2)) {
                System.out.println("lock-thread-2 get lock");
            } else {
                System.out.println("lock-thread-2 fail to get lock");
            }
        }, "lock-thread-2");

        t1.start();
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---main over---");
    }

    private static RedisClient client;
    private static StatefulRedisConnection<String, String> conn;
    private static RedisCommands<String, String> syncCMD;
    private static RedisAsyncCommands<String, String> asyncCMD;
    private static RedisReactiveCommands<String, String> reactiveCMD;

    @BeforeClass
    public static void connByLetture() {
        RedisURI redisURI = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(5, ChronoUnit.SECONDS))
                .build();
        client = RedisClient.create(redisURI);
        conn = client.connect();
        syncCMD = conn.sync();
        asyncCMD = conn.async();
        reactiveCMD = conn.reactive();
    }

    @AfterClass
    public static void closeLetture() {
        conn.close();
        client.shutdown();
    }

    @Test
    public void testLettuce() {
        SetArgs setArgs = SetArgs.Builder.nx().ex(5);
        System.out.println("--- sync mode ---");
        String result = syncCMD.get("demo");
        System.out.println(result);
        result = syncCMD.set("testLetture", "demo", setArgs);
        System.out.println("set testLetture: " + result);

        // ----------------------

        System.out.println("--- async mode ---");
        RedisFuture<String> future = asyncCMD.get("demo");
        future.thenAccept(val -> System.out.println("future get = " + val));
        System.out.println("...");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // ----------------------

        System.out.println("--- reactive mode ---");
        Mono<String> mono = reactiveCMD.get("demo");
        mono.subscribe(val -> System.out.println("reactive get = " + val));
        reactiveCMD.sadd("food", "fish", "beef", "milk").block();
        Flux<String> flux = reactiveCMD.smembers("food");
        System.out.println("reactive get food: ");
        flux.subscribe(System.out::println);
        reactiveCMD.srem("food", "fish", "beef", "milk").block();
    }
}
