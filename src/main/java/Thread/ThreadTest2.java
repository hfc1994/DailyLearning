package Thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2017/11/26.
 *
 * 测试主线程挂了其它线程能不能正常运行
 */
public class ThreadTest2
{
    public static int flag = 0;

    public static void main(String[] args)
    {
        Thread t1 = new Thread(new Runnable()
        {
            public void run()
            {
                while (true)
                {
                    if (ThreadTest2.flag == 0)
                        ThreadTest2.flag++;

                    System.out.println("t1 running");
                    try
                    {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable()
        {
            public void run()
            {
                while (true)
                {
                    if (ThreadTest2.flag == 1)
                        ThreadTest2.flag++;

                    System.out.println("t2 running");
                    try
                    {
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });

        t1.start();
        t2.start();

        while (true)
        {
            if (flag == 2)
            {
                System.out.println("flag == 2");
                String nn = "n";
                int i = Integer.valueOf(nn);
            }

            System.out.println("main running");
            try
            {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
