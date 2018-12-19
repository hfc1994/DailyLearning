package HeadFirst.DesignPattern.TemplatePattern;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class Coffee extends CaffeineBeverage
{
    @Override
    void brew()
    {
        System.out.println("Dripping Coffee through filter");
    }

    @Override
    void addCondiments()
    {
        System.out.println("Adding Sugar and Milk");
    }
}
