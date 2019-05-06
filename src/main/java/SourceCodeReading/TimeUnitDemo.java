package SourceCodeReading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2019/4/30.
 *
 * TimeUnit.SECONDS.sleep(n)会使当前线程进入休眠状态，
 *      但并不会放弃锁的持有，效果和Thread.sleep(n)一致
 * TimeUnit.SECONDS.timedWait(Object, n)会使线程进入超时等待状态，
 *      会放弃Object上面锁的持有，同时也只能在同步块中调用，效果和Object.wait(n)一致
 * TimeUnit.SECONDS.timedJoin(Thread, n)会使当前线程进入超时等待状态，
 *      等待指定线程的运行结束，效果和Thread.join(n)一致
 */
public class TimeUnitDemo {

    private static Object lock = new Object();
    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        Thread t1 = new Thread(new Thread1(lock, latch));
        Thread t2 = new Thread(new Thread2(lock));
        Thread t3 = new Thread(new Thread3());
        t1.start();
        try {
            // 确保t1比t2更早启动,t2启动后t1正处在阻塞状态
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        System.out.println("---Thread3 will not begin before Thread2 over---");
        try {
            TimeUnit.SECONDS.timedJoin(t2, 15);
//            TimeUnit.SECONDS.timedJoin(t3, 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t3.start();
        System.out.println("---main over---");
    }
}

class Thread1 implements Runnable {

    private final Object lock;
    private final CountDownLatch latch;

    public Thread1 (Object lock, CountDownLatch latch) {
        this.lock = lock;
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("---Thread1 try get lock---");
        synchronized (lock) {
            System.out.println("---Thread1 got lock---");
            try {
                System.out.println("---Thread1 start to wait 3 seconds---");
                latch.countDown();
                TimeUnit.SECONDS.timedWait(lock, 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---Thread1 wake from wait---");
        }
    }
}

class Thread2 implements Runnable {

    private final Object lock;

    public Thread2 (Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println("---Thread2 try get lock---");
        synchronized (lock) {
            System.out.println("---Thread2 got lock---");
            try {
                System.out.println("---Thread2 start to sleep 5 seconds---");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("---Thread2 wake from sleep---");
                System.out.println("---Thread2 over---");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Thread3 implements Runnable {

    @Override
    public void run() {
        System.out.println("---Thread3 running---");
        System.out.println("---Thread3 print number every 1 second for 5 second");
        for (int i=0; i<5; i++) {
            System.out.println(i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("---Thread3 over---");
    }
}
