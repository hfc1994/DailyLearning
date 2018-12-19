package HeadFirst.DesignPattern.ObserverPattern.ifc;


/**
 * Created by user-hfc on 2018/4/13.
 */
public interface Subject
{
    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers();
}
