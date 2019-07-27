package SourceCodeReading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hfc on 2019/4/12.
 *
 * 在使用lock.lock()的方式等待获取锁，lock不会在意后续过程中出现的中断信号（t.interrupt()）
 * 在使用lock.lockInterruptibly()的方式等待获取锁，lock会关注中断信号，当出现中断信号，其就会中断等待获取锁
 *
 */
public class LockInterruptiblyDemo implements Runnable {

    private Lock lock;

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }

    public static void test1 () throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();    // 首先获取锁
        TimeUnit.SECONDS.sleep(1);
        int threadNum = 8;
        List<Thread> th = new ArrayList<>(threadNum);
        for (int i=1; i<=threadNum; i++) {
            LockInterruptiblyDemo r = new LockInterruptiblyDemo();
            r.setLock(lock);
            Thread t = new Thread(r, "t" + i);
            th.add(t);
        }
        System.out.println("---start---");
        th.forEach(Thread::start);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("---interrupt---");
        // 参考test2可知，interrupt()可以使线程从阻塞状态排队等待锁的情况中恢复出来
        th.get(th.size() - 4).interrupt();
        TimeUnit.SECONDS.sleep(100);
    }

    /**
     * 从本例子中可以看出，.interrupt()可以和.unpark(t)一样使线程从.park()的阻塞中恢复过来
     *
     * 如果线程中没有使用.park()阻塞，单单对线程调用.interrupt()只会使目标线程抛出一个中断异常，
     * 而且目标线程还是会如之前一样继续执行下去
     */
    public static void test2 () throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("---t is start---");
            LockSupport.park();
            System.out.println("---t is end---");
        });

        t.start();
        System.out.println("---main thread start sleep---");
        TimeUnit.SECONDS.sleep(5);
        System.out.println("---main thread awake---");
//        LockSupport.unpark(t);
        t.interrupt();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("---main thread over---");
    }

    @Override
    public void run() {
        //                lock.lock();
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " interrupted.");
    }
}
