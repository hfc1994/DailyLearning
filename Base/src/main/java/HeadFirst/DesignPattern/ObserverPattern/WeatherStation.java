package HeadFirst.DesignPattern.ObserverPattern;

/**
 * Created by user-hfc on 2018/4/13.
 */
public class WeatherStation
{
    public static void main(String[] args)
    {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        weatherData.setMeasurements(76, 70, 32.4f);

        System.out.println();

        WeatherDataV2 weatherDataV2 = new WeatherDataV2();
        CurrentConditionsDisplayV2 currentDisplayV2 = new CurrentConditionsDisplayV2(weatherDataV2);

        weatherDataV2.setMeasurements(777, 657, 307.4f);
        weatherDataV2.setMeasurements(766, 706, 326.4f);
    }
}
