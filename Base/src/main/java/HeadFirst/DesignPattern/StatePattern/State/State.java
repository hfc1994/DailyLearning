package HeadFirst.DesignPattern.StatePattern.State;

/**
 * Created by user-hfc on 2018/4/21.
 */
public interface State
{
    public void insertQuarter();

    public void ejectQuarter();

    public void turnCrank();

    public void dispense();
}
