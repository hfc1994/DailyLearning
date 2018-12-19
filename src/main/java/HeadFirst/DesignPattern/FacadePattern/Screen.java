package HeadFirst.DesignPattern.FacadePattern;

import HeadFirst.DesignPattern.FacadePattern.ifc.Command;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class Screen implements Command
{
    @Override
    public void on()
    {
        System.out.println("Screen turn on");
    }

    @Override
    public void off()
    {
        System.out.println("Screen turn off");
    }
}
