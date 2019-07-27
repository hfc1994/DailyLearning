package HeadFirst.DesignPattern.DecoratorPattern;

import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.Beverage;

/**
 * Created by user-hfc on 2018/4/13.
 *
 * 浓缩咖啡
 */
public class Espresso extends Beverage
{
    public Espresso()
    {
        this.description = "Expresso";
    }

    @Override
    public double cost()
    {
        return 1.99;
    }
}
