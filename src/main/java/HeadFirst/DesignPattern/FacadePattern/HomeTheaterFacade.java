package HeadFirst.DesignPattern.FacadePattern;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class HomeTheaterFacade
{
    private Light light;
    private Screen screen;
    private DvdPlayer dvdPlayer;
    private CdPlayer cdPlayer;

    public HomeTheaterFacade(Light light,
                             Screen screen,
                             DvdPlayer dvdPlayer,
                             CdPlayer cdPlayer)
    {
        this.light = light;
        this.screen = screen;
        this.dvdPlayer = dvdPlayer;
        this.cdPlayer = cdPlayer;
    }

    public void watchMovie(String movieName)
    {
        System.out.println("Get ready to watch a movie..." + movieName);
        light.off();
        screen.on();
        dvdPlayer.on();
        cdPlayer.on();
    }

    public void endMovie()
    {
        System.out.println("Shutting movie theater down...");
        light.on();
        cdPlayer.off();
        dvdPlayer.off();
        screen.off();
    }
}

