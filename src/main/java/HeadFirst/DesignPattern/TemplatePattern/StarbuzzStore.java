package HeadFirst.DesignPattern.TemplatePattern;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class StarbuzzStore
{
    public static void main(String... args)
    {
        Tea tea = new Tea();
        tea.prepareRecipe();

        System.out.println("\n");

        Coffee coffee = new Coffee();
        coffee.prepareRecipe();
    }
}
