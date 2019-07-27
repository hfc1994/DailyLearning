import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2019/1/27.
 */
public class Hook {

    public static void main (String[] args) {
        System.out.println("---begin---");
        Hook t = new Hook();
        t.doShutDownHook();
        for (int i=0; i<10; i++) {
            try {
                System.out.println(i);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("---over---");
    }

    private void doShutDownHook() {
        Runtime runtime = Runtime.getRuntime();
        runtime.addShutdownHook(new Thread(() -> {
            System.out.println("---hooooooook---");
        }));
    }
}
