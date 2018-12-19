package HeadFirst.DesignPattern.DecoratorPattern.abstractClass;

/**
 * Created by user-hfc on 2018/4/13.
 *
 * 主饮料
 */
public abstract class Beverage
{
    public String description = "Unknown Beverage";

    public String getDescription()
    {
        return description;
    }

    public abstract double cost();
}
