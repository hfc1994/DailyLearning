import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by user-hfc on 2017/9/11.
 */
public class Test implements Runnable {
    private int i = 0;

    public static void main(String[] args) {
//        ReentrantLock lock = new ReentrantLock();

        Test t = new Test();
        int threadNum = 100;
        List<Thread> list = new ArrayList<>(threadNum);

        for (int j=0; j<threadNum; j++) {
            Thread th = new Thread(t);
            list.add(th);
        }

        list.forEach(Thread::start);

    }

    @Override
    public void run() {
        for(int m=0; m<100; m++) {
            System.out.println(++i);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


