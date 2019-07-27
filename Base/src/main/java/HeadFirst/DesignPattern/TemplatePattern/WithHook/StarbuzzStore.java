package HeadFirst.DesignPattern.TemplatePattern.WithHook;

/**
 * Created by user-hfc on 2018/4/18.
 */
public class StarbuzzStore
{
    public static void main(String... args)
    {
        CoffeeWithHook coffee = new CoffeeWithHook();
        coffee.prepareRecipe();
    }
}
