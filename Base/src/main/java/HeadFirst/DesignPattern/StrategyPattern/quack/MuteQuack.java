package HeadFirst.DesignPattern.StrategyPattern.quack;

/**
 * Created by user-hfc on 2018/4/11.
 */
public class MuteQuack implements QuackBehavior
{
    @Override
    public void quack()
    {
        System.out.println("<< Slicense >>");
    }
}
