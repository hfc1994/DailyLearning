package HeadFirst.DesignPattern.DecoratorPattern;

import HeadFirst.DesignPattern.DecoratorPattern.abstractClass.Beverage;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class StarbuzzCoffee
{
    public static void main(String[] args)
    {
        Beverage beverage = new Espresso();
        System.out.println(beverage.getDescription() + " $" + beverage.cost());

        Beverage beverage1 = new HouseBlend();
        beverage1 = new Mocha(beverage1);
        beverage1 = new Soy(beverage1);
        beverage1 = new Whip(beverage1);

        System.out.println(beverage1.getDescription() + " $" + beverage1.cost());
    }
}
