package HeadFirst.DesignPattern.ObserverPattern;

import HeadFirst.DesignPattern.ObserverPattern.ifc.DisplayElement;
import HeadFirst.DesignPattern.ObserverPattern.ifc.Observer;
import HeadFirst.DesignPattern.ObserverPattern.ifc.Subject;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement
{
    private float temperature;
    private float humidity;
    private Subject weatherData;

    public CurrentConditionsDisplay(Subject weatherData)
    {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temperature, float hunidity, float pressure)
    {
        this.temperature = temperature;
        this.humidity = hunidity;
        display();
    }

    @Override
    public void display()
    {
        System.out.println("Current conditions : " + temperature
                + "F degrees and " + humidity + "% humidity");
    }
}
