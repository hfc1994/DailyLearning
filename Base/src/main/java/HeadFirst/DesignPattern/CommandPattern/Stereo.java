package HeadFirst.DesignPattern.CommandPattern;

/**
 * Created by user-hfc on 2018/4/16.
 */
public class Stereo
{
    public void setCD()
    {
        System.out.println("CD input into Stereo");
    }

    public void unSetCD()
    {
        System.out.println("CD output from Stereo");
    }

    public void setVolume(int volume)
    {
        System.out.println("set volume in " + volume);
    }

    public void on()
    {
        System.out.println("Stereo on");
    }

    public void off()
    {
        System.out.println("Stereo off");
    }
}
