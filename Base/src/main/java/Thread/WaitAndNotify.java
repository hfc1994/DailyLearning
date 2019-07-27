package Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2019/3/3.
 *
 * .wait()会使对象进入一个等待“锁等待（唤醒信号）”的队列中
 * .notify()会发出一个唤醒信号，从等待队列的头部取出一个对象
 * .notifyAll()会发出一个唤醒信号，一次性取出等待队列中的全部对象
 * 上面三个方法是针对同一个对象的对象锁
 */
public class WaitAndNotify implements Runnable {

    private Random rd = new Random();
    private final List<String> list;
    private int threadId;

    public WaitAndNotify(List<String> list) {
        this.list = list;
    }

    private int getRandomNumber() {
        return rd.nextInt(100);
    }

    private void doWait() throws InterruptedException {
        synchronized (list) {
            if (list.size() == 0) {
                list.add("oops");
            } else {
                System.out.println(threadId + " is wating");
                list.wait();
            }
        }
    }

    private void doNotify() {
        synchronized (list) {
            list.notify();
        }
    }

    private void doNotifyAll() {
        synchronized (list) {
            list.notifyAll();
        }
    }

    @Override
    public void run() {
        threadId = getRandomNumber();
        System.out.println("ThreadId is --- " + threadId);
        try {
            doWait();
            System.out.println(threadId + " sleep for 5 second");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Over! " + threadId + " is over");
//            doNotify();
            doNotifyAll();
        } catch (InterruptedException e) {
            System.out.println("lock wait error");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- main start ---");
        List<String> list = new ArrayList<>(1);
        for (int i=0; i<10; i++) {
            WaitAndNotify wan = new WaitAndNotify(list);
            Thread t = new Thread(wan);
            t.start();
        }
    }
}
