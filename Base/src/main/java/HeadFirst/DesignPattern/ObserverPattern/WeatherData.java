package HeadFirst.DesignPattern.ObserverPattern;

import HeadFirst.DesignPattern.ObserverPattern.ifc.Observer;
import HeadFirst.DesignPattern.ObserverPattern.ifc.Subject;

import java.util.ArrayList;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class WeatherData implements Subject
{
    private ArrayList<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData()
    {
        observers = new ArrayList();
    }

    @Override
    public void registerObserver(Observer o)
    {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o)
    {
        int i = observers.indexOf(o);
        if (i >= 0)
        {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers()
    {
        for (Observer o : observers)
        {
            o.update(temperature, humidity, pressure);
        }
    }

    public void measurementsChanged()
    {
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
