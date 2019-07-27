package HeadFirst.DesignPattern.DecoratorPattern;

import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.Beverage;
import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.CondimentDecorator;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class Soy extends CondimentDecorator
{
    Beverage beverage;

    public Soy(Beverage beverage)
    {
        this.beverage = beverage;
    }

    @Override
    public String getDescription()
    {
        return beverage.getDescription() + ", Soy";
    }

    @Override
    public double cost()
    {
        return 0.15 + beverage.cost();
    }
}
