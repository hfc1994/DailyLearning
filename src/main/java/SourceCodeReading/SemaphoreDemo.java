package SourceCodeReading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by hfc on 2019/4/29.
 *
 * Semaphore就是一个可重入的锁，重入不仅限于当前线程
 * 可重入的次数受限于初始设置的值
 *
 * demo里面测试了以下几种情况
 * 1、单个线程多次获取Semaphore
 * 2、多个线程各获取一次Semaphore
 */
public class SemaphoreDemo {

    private static int LOCK_NUM = 5;

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(LOCK_NUM);
        Thread t1 = new Thread(new ThreadNo1(semaphore, LOCK_NUM, Thread.currentThread()));
        t1.start();
        LockSupport.park(SemaphoreDemo.class);
        System.out.println("---main ThreadNo1 test over---");

        List<Thread> th = new ArrayList<>(2 * LOCK_NUM);
        CountDownLatch latch = new CountDownLatch(2 * LOCK_NUM);
        for (int i=0; i<2*LOCK_NUM; i++) {
            Thread t2 = new Thread(new ThreadNo2(semaphore, latch), "ThreadNo2 - " + i);
            th.add(t2);
        }
        th.forEach(Thread::start);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---main ThreadNo2 test over---");
    }

}

// 用于单个线程多次重入Semaphore
class ThreadNo1 implements Runnable {

    private Semaphore semaphore;
    private int reenterNum;
    private Thread mainThread;

    public ThreadNo1(Semaphore semaphore, int lockNum, Thread thread) {
        this.semaphore = semaphore;
        this.reenterNum = lockNum;
        this.mainThread = thread;
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<reenterNum; i++) {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " get Semaphore in " + i + " times");
            }

            TimeUnit.SECONDS.sleep(5);

            for (int i=0; i<reenterNum; i++) {
                semaphore.release();
            }
            System.out.println("---ThreadNo1 is over---");
            LockSupport.unpark(mainThread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 用于多次实例，多次重入Semaphore
class ThreadNo2 implements Runnable {

    private Semaphore semaphore;
    private CountDownLatch latch;

    public ThreadNo2(Semaphore semaphore, CountDownLatch latch) {
        this.semaphore = semaphore;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " get Semaphore");

            TimeUnit.SECONDS.sleep(5);
            semaphore.release();
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}