package HeadFirst.DesignPattern.StatePattern.State;

import HeadFirst.DesignPattern.StatePattern.GumballMachine;

/**
 * Created by user-hfc on 2018/4/21.
 */
public class SoldOutState implements State
{
    private GumballMachine gumballMachine;

    public SoldOutState(GumballMachine gumballMachine)
    {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter()
    {
        System.out.println("we are sold out...");
    }

    @Override
    public void ejectQuarter()
    {
        System.out.println("we are sold out...");
    }

    @Override
    public void turnCrank()
    {
        System.out.println("we are sold out...");
    }

    @Override
    public void dispense()
    {
        System.out.println("we are sold out...");
    }
}
