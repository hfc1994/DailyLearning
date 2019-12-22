package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hfc on 2019/12/22.
 *
 * 1.3.39 环形缓冲区。环形缓冲区，又称为环形队列，是一种定长N的先进先出
 * 的数据结构。它在进程间的异步数据传输或记录日志文件时十分有用。当缓冲区
 * 为空时，消费者会在数据存入缓冲区前等待；当缓冲区满时，生产者会等待将数
 * 据存入缓冲区。为RingBuffer设计一份API并用（回环）数组将其实现。
 */
public class RingBuffer<K> {

    private K[] values;
    private int size;
    private int head;
    private int tail;

    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;

    public RingBuffer() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public RingBuffer(int capacity) {
        values = (K[]) new Object[capacity];
        size = 0;
        head = tail = 0;
        lock = new ReentrantLock();
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void put(K element) throws InterruptedException {

        try {
            lock.lock();

            while (size == values.length) {
                // .await()会使线程放弃锁
                // 当被唤醒后，只有线程获取了锁才能继续下去
                System.out.println("notFull await start");
                notFull.await();
                System.out.println("notFull await over");
            }

            values[head] = element;

            // 到数组末尾时，再添加数据就回到数组头部了
            if (head == values.length - 1)
                head = 0;
            else
                head++;

            size++;
            notEmpty.signal();  // notify()是Object的方法，需要事先获得到对应的锁
        } finally {
            lock.unlock();
        }
    }

    public K take() throws InterruptedException {
        try {
            lock.lock();

            while (size == 0) {
                // .await()会使线程放弃锁
                // 当被唤醒后，只有线程获取了锁才能继续下去
                System.out.println("notEmpty await");
                notEmpty.await();
                System.out.println("notEmpty over");
            }

            K ret = values[tail];
            values[tail] = null;

            if (tail == values.length - 1)
                tail = 0;
            else
                tail++;

            size--;
            notFull.signal();   // notify()是Object的方法，需要事先获得到对应的锁

            return ret;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        RingBuffer<String> ringBuffer = new RingBuffer<>(4);
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    System.out.println("---put invoke---");
                    ringBuffer.put("now is " + System.currentTimeMillis());
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(() -> {
           while (true) {
               try {
                   System.out.println("---take invoke---");
                   System.out.println(ringBuffer.take());
                   TimeUnit.SECONDS.sleep(1);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        });
        t2.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
