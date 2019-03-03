package Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2019/3/3.
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
            doNotify();
//            doNotifyAll();
        } catch (InterruptedException e) {
            System.out.println("lock wait error");
        }
    }

    public static void main(String[] args) {
        System.out.println("--- main start ---");
        List<String> list = new ArrayList<>(4);
        for (int i=0; i<10; i++) {
            WaitAndNotify wan = new WaitAndNotify(list);
            Thread t = new Thread(wan);
            t.start();
        }
    }
}
