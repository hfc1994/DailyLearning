package HeadFirst.DesignPattern.TemplatePattern.WithHook;

/**
 * Created by user-hfc on 2018/4/17.
 */
public abstract class CaffeineBeverageWithHook
{
    final void prepareRecipe()
    {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments())
            addCondiments();
    }

    abstract void brew();

    abstract void addCondiments();

    void boilWater()
    {
        System.out.println("Boiling water...");
    }

    void pourInCup()
    {
        System.out.println("Pouring into cup...");
    }

    boolean customerWantsCondiments()
    {
        return true;
    }
}
