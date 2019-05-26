package SourceCodeReading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2019/5/26.
 *
 * CyclicBarrier同步屏障
 * 只有全部的线程都到达了屏障(.await())，线程才会被放行，
 * 否则像是提前到达屏障的线程都会进入等待状态。
 *
 * 该工具可以被循环使用，见.nextGeneration()
 *
 * 该工具使用内置的ReentrantLock对象来保证多线程间使用时的线程安全
 * 使用ReentrantLock.newCondition()对象来使最后一个调用.await()的线程
 *      能够把提前到达的线程从等待状态中唤醒
 */
public class CyclicBarrierDemo {

    private CyclicBarrier cb = new CyclicBarrier(2);
    private CyclicBarrier cbF = new CyclicBarrier(2, new doFirst());

    public static void main(String[] args) {
       CyclicBarrierDemo cbd = new CyclicBarrierDemo();
//       cbd.test1();
        cbd.test2();
    }

    public void test1() {
        Thread t1 = new Thread(() -> {
            System.out.println("---t1 is beginning---");
            try {
                System.out.println("---t1 begin to sleep 5 seconds---");
                TimeUnit.SECONDS.sleep(5);
                cb.await();
                System.out.println("---t1 awake---");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        System.out.println("---test1 is running");
        System.out.println("---test1 begin to wait t1---");
        t1.start();
        try {
            cb.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("---test1 is over---");
    }

    public void test2() {
        Thread t2 = new Thread(() -> {
            System.out.println("---1---");
            try {
                TimeUnit.SECONDS.sleep(5);
                cbF.await();
                System.out.println("---2---");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        System.out.println("---3---");
        t2.start();
        try {
            cbF.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("---4---");
    }

    static class doFirst implements Runnable {
        @Override
        public void run() {
            System.out.println("---");
            System.out.println("I will do first.");
            System.out.println("---");
        }
    }
}
