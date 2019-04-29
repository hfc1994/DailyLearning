package SourceCodeReading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by user-hfc on 2018/6/8.
 *
 * @author user-hfc.
 *
 * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。
 * LockSupport 提供park()和unpark()方法实现阻塞线程和解除线程阻塞，LockSupport和每个使用它的线程都与一个许可(permit)关联。
 * 此处的permit使用的是信号量（Semaphore）的概念
 * permit相当于1，0的开关，默认是0，调用一次unpark就加1变成1，调用一次park会消费permit, 也就是将1变成0，同时park立即返回。
 * 再次调用park会变成block（因为permit为0了，会阻塞在这里，直到permit变为1）, 这时调用unpark会把permit置为1。
 * 每个线程都有一个相关的permit, permit最多只有一个，重复调用unpark也不会积累。
 *
 * park()和unpark()不会有 “Thread.suspend和Thread.resume所可能引发的死锁” 问题，
 * 由于许可的存在，调用 park 的线程和另一个试图将其 unpark 的线程之间的竞争将保持活性。
 *
 * LockSupport是不可重入的，如果一个线程连续2次调用LockSupport.park()，那么该线程一定会一直阻塞下去。
 *
 * 个人这么理解：
 * 每个线程都有一个permit，其取值范围在[-1,1]，初始为0，当permit>=0时，线程不会被阻塞。
 * 每调用一次LockSupport.park()就减1，每调用一次LockSupport.unpark()就加1
 *
 *
 * 关于blocker的解释（可以参见LockSupport.java第66-73行）：
 * 有3种park的方式，每一种也都有支持blocker对象参数。当线程被阻塞时，
 * 这个对象会被记录，以便监视器和诊断工具能够识别线程被阻塞的原因（这些
 * 工具可以通过getBlocker(Thread)方法来获取blockers）。强烈建议使用这
 * 种带参数的方式，而不是最原始的方式。在锁实现中作为阻塞器提供的参数通
 * 常是其本身（this）。
 *
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        LockSupportDemo lsd = new LockSupportDemo();
        lsd.basicDemo();
    }

    public void basicDemo() {
        System.out.println("111");
        LockSupport.unpark(Thread.currentThread());
//        LockSupport.unpark(Thread.currentThread());
//        LockSupport.unpark(Thread.currentThread());
        LockSupport.park();
//        LockSupport.park();
        System.out.println("222");

        Thread t = new Thread(() ->
        {
            System.out.println("--begin--");
            int i = 0;
            while (true)
            {
                i++;
                System.out.println(i);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (i % 10 == 0)
                    LockSupport.park();
            }
        });

        System.out.println("--main--");

        t.start();

        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.unpark(t);

        System.out.println("--main--");

        LockSupport.park();
    }
}
