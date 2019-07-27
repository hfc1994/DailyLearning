package HeadFirst.DesignPattern.FactoryPattern;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class ChicagoStore extends PizzaStore
{
    @Override
    public Pizza createPizza(String type)
    {
        if ("cheese".equals(type))
        {
            return new ChicagoStyleCheesePizza();
        }
        else
            return null;
    }
}
