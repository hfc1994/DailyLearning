package HeadFirst.DesignPattern.FactoryPattern.AbstractFactoryPattern;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class PizzaTestDrive
{
    public static void main(String[] args)
    {
        PizzaStore nyStore = new NYPizzaStore();
        Pizza pizza = nyStore.orderPizza("cheese");
        System.out.println("Ethan ordered a " + pizza.getName() + "\n");

        ChicagoStore chicagoStore = new ChicagoStore();
        pizza = chicagoStore.orderPizza("cheese");
        System.out.println("Joel ordered a " + pizza.getName() + "\n");
    }
}
