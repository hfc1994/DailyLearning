package HeadFirst.DesignPattern.FactoryPattern;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class NYStyleCheesePizza extends Pizza
{
    public NYStyleCheesePizza()
    {
        this.name =  "NY style Sauce and cheese Pizza";
        this.dough = "Thin Crust Dough";
        this.sauce = "Marinara Sauce";

        this.toppings.add("Granted Reggiano Cheese");
    }
}
