package com.hfc.springboot;

import com.hfc.springboot.services.RedisServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private RedisServices redisServices;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedis() {
        List<String> keys = new ArrayList<>();
        for (int i=0; i<10; i++) {
            String key = "aaa" + i;
            redisServices.insertStr(key, "how time fly" + i);
            System.out.println(redisServices.getStr(key));
            keys.add(key);
        }
        System.out.println(redisServices.getCountOfStr(keys));
        keys.remove(keys.size() - 1);
        System.out.println(redisServices.getCountOfStr(keys));

        redisServices.insertList("list", keys);
        System.out.println(redisServices.getOneFromList("list"));
        System.out.println(redisServices.getAllFromList("list"));
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
}
