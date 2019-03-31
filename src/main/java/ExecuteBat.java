import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Created by user-hfc on 2017/11/5.
 */
public class ExecuteBat {
    public static void main(String[] args) {
        System.out.println("初始化");
        System.out.println("3");
        System.out.println("2");
        System.out.println("1");
        ExecuteBat t = new ExecuteBat();
        t.invokeCMD();
    }

    public void invokeCMD() {
        System.out.println("invokeCMD");
        try {
            //执行bat文件
            Process ps = Runtime.getRuntime().exec("echo.bat");
            InputStream in = ps.getInputStream();

            int c;
            while ((c = in.read()) != -1) {
                System.out.println(c);
            }
            in.close();
            ps.waitFor();

            System.out.println("sleep 5 seconds");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("maybe is over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
