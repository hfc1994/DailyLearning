package HeadFirst.DesignPattern.StatePattern.State;

import HeadFirst.DesignPattern.StatePattern.GumballMachine;

import java.util.Random;

/**
 * Created by user-hfc on 2018/4/21.
 */
public class HasQuarterState implements State
{
    Random randomWinner = new Random(System.currentTimeMillis());
    private GumballMachine gumballMachine;

    public HasQuarterState(GumballMachine gumballMachine)
    {
        this.gumballMachine = gumballMachine;
    }

    @Override
    public void insertQuarter()
    {
        System.out.println("You can not insert another quarter");
    }

    @Override
    public void ejectQuarter()
    {
        System.out.println("Quarter returned");
        gumballMachine.setState(gumballMachine.getNoQuarterState());
    }

    @Override
    public void turnCrank()
    {
        System.out.println("You turned...");
        int winner = randomWinner.nextInt(10);
        if ((winner == 0) && (gumballMachine.getCount() > 1))
        {
            gumballMachine.setState(gumballMachine.getWinnerState());
        }
        else
            gumballMachine.setState(gumballMachine.getSoldState());
    }

    @Override
    public void dispense()
    {
        System.out.println("No gumball dispensed");
    }
}
