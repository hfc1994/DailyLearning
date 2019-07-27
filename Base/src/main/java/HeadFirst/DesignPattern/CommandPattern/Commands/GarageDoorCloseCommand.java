package HeadFirst.DesignPattern.CommandPattern.Commands;

import HeadFirst.DesignPattern.CommandPattern.GarageDoor;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class GarageDoorCloseCommand implements Command
{
    GarageDoor garageDoor;

    public GarageDoorCloseCommand(GarageDoor garageDoor)
    {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute()
    {
        garageDoor.close();
    }

    @Override
    public void undo() {
        garageDoor.open();
    }
}
