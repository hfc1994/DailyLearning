package HeadFirst.DesignPattern.ObserverPattern;

import HeadFirst.DesignPattern.ObserverPattern.ifc.DisplayElement;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class CurrentConditionsDisplayV2 implements Observer, DisplayElement
{
    private Observable observable;
    private float temperature;
    private float humidity;

    public CurrentConditionsDisplayV2(Observable observable)
    {
        this.observable = observable;
        observable.addObserver(this);
    }

    @Override
    public void display()
    {
        System.out.println("Current conditions : " + temperature
                + "F degrees and " + humidity + "% humidity");
    }

    //Observer的接口方法
    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof WeatherDataV2)
        {
            WeatherDataV2 weatherDataV2 = (WeatherDataV2) o;
            this.temperature = weatherDataV2.getTemperature();
            this.humidity = weatherDataV2.getHumidity();
            display();
        }
    }
}
