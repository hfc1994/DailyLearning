package com.hfc.springboot;

import com.hfc.springboot.services.CheckPointService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hfc on 2021/10/10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCheckPoint {

    @Autowired
    private CheckPointService cpService;

    @Test
    public void doTest() {
        System.out.println(cpService.doSomething1(1, 2, "qwer"));

        try {
            // 不能使用Integer.class来替代这里的int.class
            Method m = cpService.getClass().getMethod("doSomething1", int.class, int.class, String.class);
            System.out.println(m.invoke(cpService, 3, 4, null));

            m = cpService.getClass().getMethod("doSomething2", int.class, String.class, String.class);
            System.out.println(m.invoke(cpService, 3, "haha", "ext: "));
            System.out.println(m.invoke(cpService, 3, "oOoOo", null));
        } catch (NoSuchMethodException e) {
            System.out.println("no this method: " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("fail to invoke: " + e.getMessage());
        }
    }
}
