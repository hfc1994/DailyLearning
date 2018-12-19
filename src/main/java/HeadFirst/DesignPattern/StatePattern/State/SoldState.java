package HeadFirst.DesignPattern.StatePattern.State;

import HeadFirst.DesignPattern.StatePattern.GumballMachine;

/**
 * Created by user-hfc on 2018/4/21.
 */
public class SoldState implements State
{
    private GumballMachine gumballMachine;

    public SoldState(GumballMachine gumballMachine)
    {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter()
    {
        System.out.println("Please wait, we are already giving you a gumball");
    }

    @Override
    public void ejectQuarter()
    {
        System.out.println("Please wait, we are already turned the crank");
    }

    @Override
    public void turnCrank()
    {
        System.out.println("Turning twice does not get you another gumball");
    }

    @Override
    public void dispense()
    {
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() > 0)
            gumballMachine.setState(gumballMachine.getNoQuarterState());
        else
        {
            System.out.println("Oops, out of gumballs!");
            gumballMachine.setState(gumballMachine.getSoldOutState());
        }
    }
}
