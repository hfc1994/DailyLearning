package Thread.ConnectionDemoFromBook;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by user-hfc on 2019/4/13.
 */
public class ConnectionTest {
    private static ConnectionPool pool = new ConnectionPool(10);
    private static CountDownLatch start = new CountDownLatch(1);
    private static CountDownLatch end;
    private static AtomicInteger got = new AtomicInteger();
    private static AtomicInteger notGot = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 30;
        int cycleNum = 20;
        end = new CountDownLatch(threadNum);

        for (int i=0; i<threadNum; i++) {
            ConnectionRunner runner = new ConnectionRunner();
            runner.setPool(pool);
            runner.setCycleNum(cycleNum);
            Thread t = new Thread(runner);
            t.start();
        }

        start.countDown();
        end.await();
        System.out.println("---Test is over---");
        System.out.println("---Total is " + threadNum * cycleNum);
        System.out.println("---Success to got is " + got.intValue());
        System.out.println("---Fail to got is " + notGot.intValue());
    }

    static class ConnectionRunner implements Runnable {
        private ConnectionPool pool;
        private int cycleNum;

        public void setPool(ConnectionPool pool) {
            this.pool = pool;
        }

        public void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i=0; i<cycleNum; i++) {
                try {
                    // 从线程池里面获取连接，超时设置为1000毫秒
                    Connection conn = pool.featchConnection(1000);
                    if (conn != null) {
                        try {
                            conn.createStatement();
                            // 代理类中有休眠
                            conn.commit();
                        } finally {
                            pool.releaseConnection(conn);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception e) {
                    notGot.incrementAndGet();
                }
            }
            end.countDown();
        }
    }
}
