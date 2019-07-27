package HeadFirst.DesignPattern.StrategyPattern.fly;

/**
 * Created by user-hfc on 2018/4/11.
 */
public class FlyWithWings implements FlyBehavior
{
    @Override
    public void fly()
    {
        System.out.println("I am flying with wings");
    }
}
