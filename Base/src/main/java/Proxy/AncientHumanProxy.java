package Proxy;

/**
 * Created by hfc on 2020/6/30.
 *
 * 静态代理
 */
public class AncientHumanProxy implements IHuman {

    private IHuman target;

    public AncientHumanProxy(IHuman target) {
        this.target = target;
    }

    @Override
    public void say() {
        System.out.println("---proxy say---");
        target.say();
        System.out.println("---proxy over---");
    }

    @Override
    public void walk() {
        System.out.println("---proxy walk---");
        target.walk();
        System.out.println("---proxy over---");
    }

    @Override
    public void fight() {
        System.out.println("---proxy fight---");
        target.fight();
        System.out.println("---proxy over---");
    }
}
