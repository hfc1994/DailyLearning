package SourceCodeReading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user-hfc on 2019/4/6.
 *
 * 表明volatile变量并不能在多线程环境中避免线程同步问题
 * 如果要避免线程同步问题，需要使用具有CAS功能的原子操作类，比如AtomicInteger
 *
 * volatile的作用是使变量的改变能够立即被其它线程所知晓
 * 一个改变变量值的操作包括三个指令：读取、改变和赋值，volatile变量无法保证三个指令的原子执行
 *
 */
public class VolatileAndCAS {

    private int count = 0;
    private volatile int vCount = 0;
    private AtomicInteger casCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 30;
        int cycleNum = 100;
        List<Thread> th = new ArrayList<>(threadNum);
        CountDownLatch cdl = new CountDownLatch(threadNum);
        VolatileAndCAS vac = new VolatileAndCAS();

        for (int i=0; i<threadNum; i++) {
            Thread t = new Thread(() -> {
                for (int j=0; j<cycleNum; j++) {
                    vac.count++;
                    vac.vCount++;

                    int value;
                    do {
                        value = vac.casCount.intValue();
                    } while (!vac.casCount.compareAndSet(value, value+1));

                    try {
                        TimeUnit.MILLISECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                cdl.countDown();
            });
            th.add(t);
        }

        System.out.println("---start---");
        th.forEach(Thread::start);
        cdl.await();

        System.out.println("The count excepted " + threadNum * cycleNum + " and actually is " + vac.count);
        System.out.println("The vCount excepted " + threadNum * cycleNum + " and actually is " + vac.vCount);
        System.out.println("The casCount excepted " + threadNum * cycleNum + " and actually is " + vac.casCount.intValue());

        System.out.println("---end---");
    }
}
