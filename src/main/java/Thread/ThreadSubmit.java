package Thread;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 * Created by user-hfc on 2018/6/8.
 *
 * @author user-hfc.
 */
public class ThreadSubmit {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        Future f = executor.submit(() -> {
            boolean flag = true;
            int iFlag = 0;
            while (flag) {
                System.out.println("--thread--");
                iFlag ++;
                if (iFlag == 10)
                    flag = false;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {}
            }
        });

        try {
            System.out.println("--start--");
            //阻塞，等待线程返回值
            f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("---over---");
    }
}
