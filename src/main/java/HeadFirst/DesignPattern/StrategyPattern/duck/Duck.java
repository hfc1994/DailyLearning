package HeadFirst.DesignPattern.StrategyPattern.duck;

import HeadFirst.DesignPattern.StrategyPattern.fly.FlyBehavior;
import HeadFirst.DesignPattern.StrategyPattern.quack.QuackBehavior;

/**
 * Created by user-hfc on 2018/4/11.
 */
public abstract class Duck
{
    FlyBehavior flyBehavior;
    QuackBehavior quackBehavior;

    public Duck(){}

    public abstract void display();

    public void performFly()
    {
        flyBehavior.fly();
    }

    public void performQuack()
    {
        quackBehavior.quack();
    }

    public void swim()
    {
        System.out.println("All ducks float, even decoys!");
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
