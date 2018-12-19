package com.hfc;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2017/11/5.
 */
public class Test3
{
    public void invokeCMD()
    {
        System.out.println("invokeCMD");

        int num = 0;

        while (true)
        {
            System.out.println("第" + num + "次");
            try
            {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            num++;

            if (num % 5 == 0)
            {
                System.exit(1);
            }
        }
//        System.out.println("invokeCMD");
//        try
//        {
//            //执行bat文件
//            Process ps = Runtime.getRuntime().exec("echo.bat");
//            InputStream in = ps.getInputStream();
//
//            int c;
//            while ((c = in.read()) != -1)
//            {
//                System.out.println(c);
//            }
//            in.close();
//            ps.waitFor();
//
//            System.out.println("sleep 5 seconds");
//            TimeUnit.SECONDS.sleep(5);
//            System.out.println("maybe is over");
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
    }
}
