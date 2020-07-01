package Proxy;

/**
 * Created by hfc on 2020/6/30.
 */
public class ProxyTest {

    public static void main(String[] args) {
        // 静态代理
        AncientHumanProxy proxy = new AncientHumanProxy(new AncientHuman());
        proxy.say();
        proxy.walk();
        proxy.fight();

        // 动态代理
        System.out.println();
        IHuman human = (IHuman) new ProxyFactory(new AncientHuman()).getProxyInstance();
        System.out.println(human.getClass());
        human.say();
        human.walk();

//        System.out.println();
//        // 会出现java.lang.ClassCastException: com.sun.proxy.$Proxy1 cannot be cast to Proxy.DiffHuman
//        // 只有当目标类有实现接口才能进行Java原生动态代理，比如DiffHuman不行，AncientHuman可以
//        DiffHuman diffHuman = (DiffHuman) new ProxyFactory(new DiffHuman()).getProxyInstance();
//        diffHuman.say();
//        diffHuman.walk();

        // cglib代理
        System.out.println();
        IHuman human2 = (IHuman) new ProxyFactoryCglib(new AncientHuman()).getProxyInstance();
        System.out.println(human2.getClass());
        human2.say();
        human2.walk();

        System.out.println();
        // cglib不管目标类是否有实现接口，均能进行动态代理
        DiffHuman diffHuman2 = (DiffHuman) new ProxyFactoryCglib(new DiffHuman()).getProxyInstance();
        diffHuman2.say();
        diffHuman2.walk();
    }
}
