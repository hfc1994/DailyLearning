package SourceCodeReading;

/**
 * Created by user-hfc on 2019/5/8.
 *
 * Thread.sleep(long)是使当前线程进入超时等待阶段；
 *      当超时之后，线程从timed_wait进入到runnable，等待处理器分配时间片.
 * Thread.yield()表示线程主动放弃cpu时间片，从running进入到runnable，等待处理器分配时间片
 *      通过.yield()从运行态转为就绪态的线程，可能会又立即被分配时间片而成为运行态。
 * Thread.join()会使线程进入一个等待Thread结束的等待状态中。
 */
public class ThreadDemo {

    private static Object lock = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Thread1(lock), "Thread1");
        Thread t2 = new Thread(new Thread2(lock), "Thread2");
        t1.start();
        t2.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---main is over---");
    }

    static class Thread1 implements Runnable {

        private final Object lock;

        public Thread1 (Object object) {
            this.lock = object;
        }

        @Override
        public void run() {
            synchronized (this.lock) {
                System.out.println("---Thread1 get lock---");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---Thread1 wake from sleep---");
            }
        }
    }

    static class Thread2 implements Runnable {

        private final Object lock;

        public Thread2 (Object object) {
            this.lock = object;
        }

        @Override
        public void run() {
            synchronized (this.lock) {
                System.out.println("---Thread2 get lock---");
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("---Thread2 wake from sleep---");
            }
        }
    }
}
