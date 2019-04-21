package SourceCodeReading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user-hfc on 2019/4/21.
 *
 * Condition可以用于在当前线程获取锁之后，通过.await()暂时释放锁的占有，并阻塞住
 * 当其它线程调用.signal()或.signalAll()之后，当前线程再次进入获取锁的流程中
 * 当再次获取到锁之后，当前线程会从.await()的阻塞中恢复，继续执行后面的语句
 *
 */
public class ConditionDemo {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Thread t1 = new Thread(() -> {
           lock.lock();
            System.out.println("--- t1 do lock---");
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("--- t1 will do await---");
                condition.await();
                System.out.println("--- t1 awake up---");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
            System.out.println("--- t1 do unlock---");
        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            System.out.println("---t2 do lock---");
            System.out.println("---t2 will do signal---");
            condition.signal();
//            condition.signalAll();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
            System.out.println("---t2 do unlock---");
        });

        t1.start();
        t2.start();
    }
}
