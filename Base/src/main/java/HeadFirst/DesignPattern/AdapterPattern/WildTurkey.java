package HeadFirst.DesignPattern.AdapterPattern;

import HeadFirst.DesignPattern.AdapterPattern.ifc.Turkey;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class WildTurkey implements Turkey
{
    @Override
    public void gobble()
    {
        System.out.println("Gobble gobble");
    }

    @Override
    public void fly()
    {
        System.out.println("I am flying a short distance");
    }
}
