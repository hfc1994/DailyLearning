package HeadFirst.DesignPattern.AdapterPattern;

import HeadFirst.DesignPattern.AdapterPattern.ifc.Duck;
import HeadFirst.DesignPattern.AdapterPattern.ifc.Turkey;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class TurkeyAdapter implements Duck
{
    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey)
    {
        this.turkey = turkey;
    }

    @Override
    public void quack()
    {
        turkey.gobble();
    }

    @Override
    public void fly()
    {
        turkey.fly();
    }
}
