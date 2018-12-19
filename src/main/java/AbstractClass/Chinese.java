package AbstractClass;

/**
 * Created by user-hfc on 2018/4/11.
 */
public class Chinese extends Person
{
    @Override
    public int getHeight()
    {
        return 2;
    }

    public static void main(String... args)
    {
        Person c = new Chinese();
        System.out.println(c.getHeight());
        System.out.println(c.getType());
    }
}
