package Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by hfc on 2020/6/30.
 *
 * 动态代理
 */
public class ProxyFactory {

    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Object returnVal;
                    if ("say".equals(method.getName())) {
                        System.out.println("---proxy say begin---");
                        returnVal = method.invoke(target, args);
                        System.out.println("---proxy say end---");
                    } else {
                        System.out.println("---proxy begin---");
                        returnVal = method.invoke(target, args);
                        System.out.println("---proxy end---");
                    }

                    return returnVal;
                });
    }
}
