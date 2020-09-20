package com.hfc.springboot;

import com.hfc.springboot.services.AspectsRedisServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hfc on 2020/7/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAspect {

    @Autowired
    private AspectsRedisServices redisServices;

    @Test
    public void testAspect() {
        System.out.println("--- invoke insertStr");
        redisServices.insertStr("demo", "aspect");
        System.out.println();

        System.out.println("--- invoke getStr");
        String ret = redisServices.getStr("demo");
        System.out.println("ret = " + ret);
        System.out.println();

        System.out.println("--- invoke getCountOfStr");
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "demo", "demo2", "demo3");
        // 返回值不匹配，所以方法没有被拦截
        Long lRet = redisServices.getCountOfStr(list);
        System.out.println(lRet);
        System.out.println();

        System.out.println("--- invoke getStrWithException");
        try {
            String ret2 = redisServices.getStrWithException("demo");
            System.out.println(ret2);
        } catch (Throwable t) {
            System.out.println("exception: " + t.getMessage());
        }
        System.out.println();

        System.out.println("--- invoke setAndGet");
        String ret3 = redisServices.setAndGet("demo", "newAspect");
        System.out.println("ret3 = " + ret3);
        System.out.println();
    }
}
