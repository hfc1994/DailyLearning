package HeadFirst.DesignPattern.StrategyPattern.duck;

import HeadFirst.DesignPattern.StrategyPattern.fly.FlyWithWings;
import HeadFirst.DesignPattern.StrategyPattern.quack.Quack;

/**
 * Created by user-hfc on 2018/4/11.
 */
public class MallardDuck extends Duck
{
    public MallardDuck()
    {
        this.flyBehavior = new FlyWithWings();
        this.quackBehavior = new Quack();
    }

    @Override
    public void display()
    {
        System.out.println("I am a real Mallard duck");
    }
}
