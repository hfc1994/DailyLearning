package SourceCodeReading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
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
        Lock lock = new ReentrantLock();
        lock.lock();    // 首先获取锁
        TimeUnit.SECONDS.sleep(1);
        int threadNum = 3;
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
        // @todo 后续的线程在队列中等待锁（LockSupport.park(this)），为什么会被中断
        th.get(th.size() - 1).interrupt();
        TimeUnit.SECONDS.sleep(100);
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
