package SourceCodeReading;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2019/5/26.
 *
 * Exchanger用于在两个线程之间交换数据，当一个线程调用.exchange(msg)后，
 * 如果没有第二个线程调用.exchange(msg)，那么线程就会被阻塞在这里
 *
 */
public class ExchangerDemo {

    private static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                System.out.println("---t1 正在等待消息---");
                String message = exchanger.exchange("I am t1.");
                System.out.println("---t1 收到消息---" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("---t2 正在等待消息---");
                String message = exchanger.exchange("I am t2");
                System.out.println("---t2 收到消息---" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
