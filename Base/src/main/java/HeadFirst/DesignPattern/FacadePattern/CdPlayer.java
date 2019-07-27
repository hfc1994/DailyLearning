package HeadFirst.DesignPattern.FacadePattern;

import HeadFirst.DesignPattern.FacadePattern.ifc.Command;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class CdPlayer implements Command
{
    @Override
    public void on()
    {
        System.out.println("CdPlayer turn on");
    }

    @Override
    public void off()
    {
        System.out.println("CdPlayer turn off");
    }
}
