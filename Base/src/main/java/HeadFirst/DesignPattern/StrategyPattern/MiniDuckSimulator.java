package HeadFirst.DesignPattern.StrategyPattern;

import HeadFirst.DesignPattern.StrategyPattern.duck.Duck;
import HeadFirst.DesignPattern.StrategyPattern.duck.MallardDuck;
import HeadFirst.DesignPattern.StrategyPattern.duck.ModelDuck;
import HeadFirst.DesignPattern.StrategyPattern.fly.FlyRocketPowered;

/**
 * Created by user-hfc on 2018/4/11.
 */
public class MiniDuckSimulator
{
    public static void main(String... args)
    {
        Duck mallard = new MallardDuck();
        mallard.performFly();
        mallard.performQuack();
        mallard.display();
        mallard.swim();

        System.out.println("-----");

        Duck model = new ModelDuck();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly();
    }
}
