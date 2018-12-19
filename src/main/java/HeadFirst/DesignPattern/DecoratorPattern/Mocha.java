package HeadFirst.DesignPattern.DecoratorPattern;

import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.Beverage;
import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.CondimentDecorator;

/**
 * Created by user-hfc on 2018/4/13.
 *
 * 配料摩卡
 */
public class Mocha extends CondimentDecorator
{
    Beverage beverage;

    public Mocha(Beverage beverage)
    {
        this.beverage = beverage;
    }

    @Override
    public String getDescription()
    {
        return beverage.getDescription() + ", Mocha";
    }

    @Override
    public double cost()
    {
        return 0.2 + beverage.cost();
    }
}
