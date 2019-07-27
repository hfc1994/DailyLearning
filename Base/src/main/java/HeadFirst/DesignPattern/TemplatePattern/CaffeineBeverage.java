package HeadFirst.DesignPattern.TemplatePattern;

/**
 * Created by user-hfc on 2018/4/17.
 */
public abstract class CaffeineBeverage
{
    final void prepareRecipe()
    {
        boilWater();
        brew();
        pourInCup();
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
}
