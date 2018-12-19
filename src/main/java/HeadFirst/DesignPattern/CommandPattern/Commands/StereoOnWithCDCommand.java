package HeadFirst.DesignPattern.CommandPattern.Commands;

import HeadFirst.DesignPattern.CommandPattern.Stereo;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class StereoOnWithCDCommand implements Command
{
    Stereo stereo;

    public StereoOnWithCDCommand(Stereo stereo)
    {
        this.stereo = stereo;
    }

    @Override
    public void execute()
    {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(10);
    }

    @Override
    public void undo()
    {
        stereo.setVolume(0);
        stereo.unSetCD();
        stereo.off();
    }
}
