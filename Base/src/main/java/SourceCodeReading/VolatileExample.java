package SourceCodeReading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2020/5/20.
 */
public class VolatileExample {

    private static volatile int volatileVar = 0;
    private static int nonVolatileVar = 1;

    public static void main(String[] args) {
//        example1();
        example2();
    }

    public static void example1() {
        new Thread(() -> {
            try { TimeUnit.SECONDS.sleep(1); }
            catch (InterruptedException e) { e.printStackTrace(); }

            // TODO: 2020/5/20 这两种为什么会存在不确定性需要研究
            VolatileExample.nonVolatileVar = 100;    // 存在不确定性
            VolatileExample.volatileVar = 99;        // 可能时 1 1 100，也可能时 1 100 100


//            VolatileExample.volatileVar = 99;        // 存在不确定性
//            VolatileExample.nonVolatileVar = 100;      // 可能时 1 1 100，也可能时 1 100 100

            int i=0;
            while (true) {
                if (i<1000)
                    i++;
                else
                    i = i - 100;
            }
        }).start();

        int temp0 = VolatileExample.nonVolatileVar;
        int temp1 = 0;
        while (VolatileExample.volatileVar == 0) {
            temp1 = VolatileExample.nonVolatileVar;
        }

        int temp2 = VolatileExample.nonVolatileVar;
        System.out.println(temp0);
        System.out.println(temp1);
        System.out.println(temp2);
    }

    /**
     * 想测试线程切换上下文时，线程的修改是否会被刷入主存
     * 但是就算会被刷入主存，但是main线程可能不读取主存了
     */
    public static void example2() {
        int threadCount = 30;
        List<Thread> ts = new ArrayList<>();

        for (int i=0; i<threadCount; i++) {
            Thread t = new Thread(() -> {

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                VolatileExample.nonVolatileVar++;

                int j=0;
                while (true) {
                    if (j < 1000)
                        j++;
                    else
                        j = j - 100;
                }
            });
            ts.add(t);
        }

        ts.forEach(Thread::start);

        // 额外启动的线程中如果不增加sleep代码段，
        // 那么此处就不会出现无限循环
        // 猜测可能是循环达到一定次数，main线程就不会去主存里读取nonVolatileVar了
        // 所以不加sleep会使nonVolatileVar较早被更新，然后被检测到
        while (nonVolatileVar == 1) {}
        System.out.println("---over---");
        System.out.println(nonVolatileVar);
    }
}
