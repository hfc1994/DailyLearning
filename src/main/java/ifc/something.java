package ifc;

/**
 * Created by user-hfc on 2018/1/14.
 */
public class something implements jump,run
{
    @Override
    public void slow_run()
    {
        System.out.println("slow_run");
    }

    @Override
    public void fast_run()
    {
        System.out.println("fast_run");
    }

    @Override
    public void low_jump()
    {
        System.out.println("low_jump");
    }

    @Override
    public void high_jump()
    {
        System.out.println("high_jump");
    }

    @Override
    public void stop()
    {
        System.out.println("stop");
    }

    public static void main(String[] args)
    {
        something s = new something();
        s.stop();

        run r = new something();
        r.stop();

        jump j = new something();
        j.stop();

        somewhat ss = new somewhat(6);
        System.out.println(ss.getNum());
    }
}

class somewhat
{
    int num = 0;

    public somewhat(int i)
    {
        this.num = i;
    }

    public int getNum()
    {
        return num*10;
    }

    public static void main(String args)
    {
        somewhat ss = new somewhat(7);
        System.out.println(ss.getNum());
    }
}