package com.hfc.springboot.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by hfc on 2020/7/1.
 *
 * 日志切面类
 */
@Aspect
public class LogAspects {

    // 返回值需要是String的方法
    @Pointcut("execution(public String com.hfc.springboot.services.AspectsRedisServices.*(..))")
    public void pointCut() {}

    // 对注解做切面，实现了自定义的注解
    @Pointcut("@annotation(com.hfc.springboot.annotation.CheckPoint)")
    public void checkPointConfig() {}

//    // 可以使用这种方式配合checkAroundForHint()来带上当时的CheckPoint实例
//    @Pointcut("@annotation(checkPoint)")
//    public void checkPointConfig(CheckPoint checkPoint) {}

    @Before("pointCut()")
    public void logStart() {
        System.out.println("@Before：在方法调用前使用...");
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

    @Around("checkPointConfig()")
    public Object checkAround(ProceedingJoinPoint joinPoint) throws Throwable {

//        // 运行时简单更改参数
//        Object[] args = joinPoint.getArgs();
//        if (args.length == 0) {
//            System.out.println("has no args");
//        } else if (args.length == 3 && args[2] == null) {
//          args[2] = "why no arg: ";
//        }
//        Object obj = joinPoint.proceed(args);   // 执行方法

        // 运行时精确更改参数
        Object[] args = joinPoint.getArgs();
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        String[] argNames = ms.getParameterNames();
        for (int i = 0; i < argNames.length; i++) {
            // 找到指定名称的属性，然后判断是否有值，没有则补上
            if ("extInfo".equals(argNames[i]) && args[i] == null) {
                args[i] = "ext_from_aop: ";
            }
        }

        // 有参数为null就提前终止
        for (int i=0; i<args.length; i++) {
            if (args[i] == null) {
                return null;
            }
        }

        Object obj = joinPoint.proceed(args);
        return obj;
    }

//    @Around("checkPointConfig(checkPoint)")
//    public Object checkAroundForHint(ProceedingJoinPoint joinPoint, CheckPoint checkPoint) throws Throwable {
//        System.out.println(checkPoint.hint());
//        return joinPoint.proceed();
//    }
}
