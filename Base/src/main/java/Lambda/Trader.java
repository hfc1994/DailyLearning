package Lambda;

/**
 * Created by user-hfc on 2018/11/30.
 *
 * @author user-hfc.
 */
public class Trader {

    private String name;
    private String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String newCity) {
        this.city = newCity;
    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}
