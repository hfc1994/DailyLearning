package Algorithm;

/**
 * Created by hfc on 2019/12/5.
 *
 * 牛顿迭代法，用以计算一个数的平方根
 */
public class NewtonMethod {

    public static void main(String args[]) {
        double precision = 0.000001d;
        double origin = 4523424d;

        double x = origin;
        double y = (x + origin / x) / 2;
        int count = 1;
        while (Math.abs(x - y) > precision) {
            count++;
            x = y;
            y = (x + origin / x) / 2;
        }
        System.out.println("count is " + count);
        System.out.println("result = " + x);
        System.out.println("actual precision = " + (x*x - origin));
    }
}
