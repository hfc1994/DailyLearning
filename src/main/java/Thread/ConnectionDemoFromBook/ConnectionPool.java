package Thread.ConnectionDemoFromBook;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2019/4/13.
 */
public class ConnectionPool {
    final private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i=0; i<initialSize; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection featchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long future = System.currentTimeMillis() + mills;
                long remains = mills;
                while (pool.isEmpty() && remains > 0) {
                    pool.wait(remains);
                    remains = future - System.currentTimeMillis();
                }

                if (pool.isEmpty())
                    return null;
                else
                    return pool.removeFirst();
            }
        }
    }
}
