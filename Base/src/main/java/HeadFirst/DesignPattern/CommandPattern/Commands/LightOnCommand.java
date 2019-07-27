package HeadFirst.DesignPattern.CommandPattern.Commands;

import HeadFirst.DesignPattern.CommandPattern.Light;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class LightOnCommand implements Command
{
    Light light;

    public LightOnCommand(Light light)
    {
        this.light = light;
    }

    @Override
    public void execute()
    {
        light.on();
    }

    @Override
    public void undo()
    {
        light.off();
    }
}
