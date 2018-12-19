package HeadFirst.DesignPattern.FactoryPattern.AbstractFactoryPattern;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class NYPizzaStore extends PizzaStore
{
    @Override
    public Pizza createPizza(String type)
    {
        if ("cheese".equals(type))
        {
            return new NYStyleCheesePizza();
        }
        else
        {
            return null;
        }
    }
}
