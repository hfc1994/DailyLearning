package HeadFirst.DesignPattern.FacadePattern;

/**
 * Created by user-hfc on 2018/4/17.
 */
public class HomeTheaterTestDrive
{
    public static void main(String... args)
    {
        Light light = new Light();
        Screen screen = new Screen();
        DvdPlayer dvdPlayer = new DvdPlayer();
        CdPlayer cdPlayer = new CdPlayer();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(light, screen, dvdPlayer, cdPlayer);
        homeTheater.watchMovie("one day");
        System.out.println("\n95 minutes later... \n");
        homeTheater.endMovie();
    }
}
