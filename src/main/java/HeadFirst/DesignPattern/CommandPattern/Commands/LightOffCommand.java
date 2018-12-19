package HeadFirst.DesignPattern.CommandPattern.Commands;

import HeadFirst.DesignPattern.CommandPattern.Light;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class LightOffCommand implements Command
{
    Light light;

    public LightOffCommand(Light light)
    {
        this.light = light;
    }

    @Override
    public void execute()
    {
        light.off();
    }

    @Override
    public void undo()
    {
        light.on();
    }
}
