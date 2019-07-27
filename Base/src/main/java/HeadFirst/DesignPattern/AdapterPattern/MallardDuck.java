package HeadFirst.DesignPattern.AdapterPattern;

import HeadFirst.DesignPattern.AdapterPattern.ifc.Duck;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class MallardDuck implements Duck
{
    @Override
    public void quack()
    {
        System.out.println("Quack");
    }

    @Override
    public void fly()
    {
        System.out.println("I am flying");
    }
}
