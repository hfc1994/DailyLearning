package Proxy;

/**
 * Created by hfc on 2020/6/30.
 *
 * 实现了IHuman接口的古人类
 */
public class AncientHuman implements IHuman {
    @Override
    public void say() {
        System.out.println("呜啦啦啦");
    }

    @Override
    public void walk() {
        System.out.println("双手双脚走");
    }

    @Override
    public void fight() {
        System.out.println("徒手搏斗");
    }
}
