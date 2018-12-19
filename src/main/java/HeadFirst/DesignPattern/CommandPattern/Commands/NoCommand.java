package HeadFirst.DesignPattern.CommandPattern.Commands;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class NoCommand implements Command
{
    @Override
    public void execute()
    {
        System.out.println("There is no command");
    }

    @Override
    public void undo()
    {
        System.out.println("There is nothing undo");
    }
}
