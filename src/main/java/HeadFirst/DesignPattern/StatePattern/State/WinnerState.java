package HeadFirst.DesignPattern.StatePattern.State;

import HeadFirst.DesignPattern.StatePattern.GumballMachine;

/**
 * Created by user-hfc on 2018/4/22.
 */
public class WinnerState implements State
{
    private GumballMachine gumballMachine;

    public WinnerState(GumballMachine gumballMachine)
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
        System.out.println("YOU ARE A WINNER! You get two gumballs for your quarter");
        gumballMachine.releaseBall();
        if (gumballMachine.getCount() == 0)
            gumballMachine.setState(gumballMachine.getSoldOutState());
        else
        {
            gumballMachine.releaseBall();
            if (gumballMachine.getCount() > 0)
                gumballMachine.setState(gumballMachine.getNoQuarterState());
            else
            {
                System.out.println("Oops, Out of gumballs!");
                gumballMachine.setState(gumballMachine.getSoldOutState());
            }
        }
    }
}
