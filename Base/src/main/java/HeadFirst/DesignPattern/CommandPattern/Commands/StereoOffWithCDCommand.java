package HeadFirst.DesignPattern.CommandPattern.Commands;

import HeadFirst.DesignPattern.CommandPattern.Stereo;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class StereoOffWithCDCommand implements Command
{
    Stereo stereo;

    public StereoOffWithCDCommand(Stereo stereo)
    {
        this.stereo = stereo;
    }

    @Override
    public void execute()
    {
        stereo.setVolume(0);
        stereo.unSetCD();
        stereo.off();
    }

    @Override
    public void undo()
    {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(10);
    }
}
