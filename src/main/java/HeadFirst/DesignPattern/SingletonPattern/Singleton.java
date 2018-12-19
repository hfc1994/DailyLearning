package HeadFirst.DesignPattern.SingletonPattern;

/**
 * Created by user-hfc on 2018/4/16.
 *
 * 双重检查加锁
 */
public class Singleton
{
    private volatile  static Singleton uniqueInstance;

    private Singleton(){}

    public static Singleton getInstance()
    {
        if (null == uniqueInstance)
        {
            synchronized(Singleton.class)
            {
                if (null == uniqueInstance)
                {
                    uniqueInstance = new Singleton();
                }
            }
        }

        return uniqueInstance;
    }
}
