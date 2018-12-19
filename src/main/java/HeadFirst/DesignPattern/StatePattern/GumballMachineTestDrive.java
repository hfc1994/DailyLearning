package HeadFirst.DesignPattern.StatePattern;

/**
 * Created by user-hfc on 2018/4/22.
 */
public class GumballMachineTestDrive
{
    public static void main(String[] args)
    {
        GumballMachine gumballMachine = new GumballMachine(5);

        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println(gumballMachine);

        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();
        gumballMachine.insertQuarter();
        gumballMachine.turnCrank();

        System.out.println(gumballMachine);
    }
}
