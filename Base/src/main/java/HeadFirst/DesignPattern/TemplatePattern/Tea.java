package HeadFirst.DesignPattern.TemplatePattern;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class Tea extends CaffeineBeverage
{
    @Override
    void brew()
    {
        System.out.println("Steeping the tea");
    }

    @Override
    void addCondiments()
    {
        System.out.println("Adding Lemon");
    }
}
