package HeadFirst.DesignPattern.DecoratorPattern;

import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.Beverage;

/**
 * Created by user-hfc on 2018/4/13.
 *
 * 秘制浓缩咖啡
 */
public class HouseBlend extends Beverage
{
    public HouseBlend()
    {
        this.description = "House Blend Coffee";
    }

    @Override
    public double cost()
    {
        return 0.89;
    }
}
