package HeadFirst.DesignPattern.CommandPattern.Commands;

import HeadFirst.DesignPattern.CommandPattern.GarageDoor;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class GarageDoorOpenCommand implements Command
{
    GarageDoor garageDoor;

    public GarageDoorOpenCommand(GarageDoor garageDoor)
    {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute()
    {
        garageDoor.open();
    }

    @Override
    public void undo()
    {
        garageDoor.close();
    }
}
