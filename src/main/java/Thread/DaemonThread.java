package Thread;

import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2019/4/11.
 *
 * 当一个Java虚拟机中不存在非Daemon线程的时候，Java虚拟机将会退出。
 * Java虚拟机在退出Daemon线程中的finally语句块并不一定会被执行。
 */
public class DaemonThread implements Runnable {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new DaemonThread());
        t.setDaemon(true);
        t.start();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("---main is over---");
    }

    @Override
    public void run() {
        try {
            for (int i=0; i<10; i++) {
                System.out.println("---daemon---" + i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("---daemon is over---");
        }
    }
}
