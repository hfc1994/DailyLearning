package HeadFirst.DesignPattern.ObserverPattern;

import java.util.Observable;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class WeatherDataV2 extends Observable
{
    private float temperature;
    private float humidity;
    private float pressure;

    public void measurementsChanged()
    {
        setChanged();
        notifyObservers();
    }

    public void setMeasurements(float temperature, float humidity, float pressure)
    {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }

    public float getTemperature()
    {
        return temperature;
    }

    public float getHumidity()
    {
        return humidity;
    }

    public float getPressure()
    {
        return pressure;
    }
}
