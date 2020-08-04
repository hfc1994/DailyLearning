import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by user-hfc on 2017/9/11.
 */
public class Test {

    public static void main(String[] args) {
        char c = '\uffff';
        System.out.println((int) c);

        int[] ii = new int[10];
        ii[3] = 5;
        ii[3]++;
        ii[3]++;
        System.out.println(ii[3]);
    }
}


