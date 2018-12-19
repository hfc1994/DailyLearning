package HeadFirst.DesignPattern.DecoratorPattern;

import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.Beverage;
import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.CondimentDecorator;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class Whip extends CondimentDecorator
{
    Beverage beverage;

    public Whip(Beverage beverage)
    {
        this.beverage = beverage;
    }

    @Override
    public String getDescription()
    {
        return beverage.getDescription() + ", Whip";
    }

    @Override
    public double cost()
    {
        return 0.25 + beverage.cost();
    }
}
