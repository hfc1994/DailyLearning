package com.hfc.springboot.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by hfc on 2020/7/1.
 *
 * 日志切面类
 */
@Aspect
public class LogAspects {

    @Pointcut("execution(public String com.hfc.springboot.services.RedisServices.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void logStart() {
        System.out.println("@Before：在方法调用前使用...{}");
    }

    @After("pointCut()")
    public void logEnd() {
        System.out.println("@After：在方法调用结束使用...");
    }

    @AfterReturning("pointCut()")
    public void logReturn() {
        System.out.println("@AfterReturning：在方法调用正常返回使用...");
    }

    @AfterThrowing("pointCut()")
    public void logException() {
        System.out.println("@AfterThrowing：在方法调用异常后使用...");
    }

    @Around("pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("@Around：方法调用前");
        Object[] args = joinPoint.getArgs();
        System.out.println("args = " + args[0]);    // 该测试符合条件的方法只有一个参数
//        joinPoint.getSourceLocation();    // 获取切面信息
//        joinPoint.getSignature();     // 获取切点签名相关的信息
        Object obj = joinPoint.proceed();   // 执行方法
        System.out.println("@Around：方法调用后");
        return obj;
    }

}
