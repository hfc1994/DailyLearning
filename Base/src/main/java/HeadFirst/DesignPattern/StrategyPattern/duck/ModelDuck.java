package HeadFirst.DesignPattern.StrategyPattern.duck;

import HeadFirst.DesignPattern.StrategyPattern.fly.FlyNoWay;
import HeadFirst.DesignPattern.StrategyPattern.quack.Quack;

/**
 * Created by user-hfc on 2018/4/11.
 */
public class ModelDuck extends Duck
{
    public ModelDuck()
    {
        this.flyBehavior = new FlyNoWay();
        this.quackBehavior = new Quack();
    }

    @Override
    public void display()
    {
        System.out.println("I am a model duck");
    }
}
