package Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by hfc on 2020/6/30.
 *
 * Cglib代理工厂
 */
public class ProxyFactoryCglib implements MethodInterceptor {

    private Object target;

    public ProxyFactoryCglib(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer en = new Enhancer();
        // 设置父类
        en.setSuperclass(target.getClass());
        // 设置回调函数
        en.setCallback(this);
        // 创建子类（代理对象）
        return en.create();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object returnVal;
        if ("say".equals(method.getName())) {
            System.out.println("---cglib say begin---");
            returnVal = method.invoke(target, args);
            System.out.println("---cglib say end---");
        } else {
            System.out.println("---cglib begin---");
            returnVal = method.invoke(target, args);
            System.out.println("---cglib end---");
        }
        return returnVal;
    }
}
