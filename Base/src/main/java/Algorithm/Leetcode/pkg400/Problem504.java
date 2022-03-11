package Algorithm.Leetcode.pkg400;

/**
 * Created by hfc on 2022/3/7.
 *
 * 504. 七进制数
 *
 * 给定一个整数 num，将其转化为 7 进制，并以字符串形式输出。
 *
 * 示例 1:
 * 输入: num = 100
 * 输出: "202"
 *
 * 示例 2:
 * 输入: num = -7
 * 输出: "-10"
 *
 * 提示：
 * -107 <= num <= 107
 *
 */
public class Problem504 {

    /**
     * 速度 100%
     * 内存 33%
     */
    public String convertToBase7(int num) {
        if (num < 7 && num > -7) return String.valueOf(num);

        boolean negative = false;
        if (num < 0) {
            negative = true;
            num *= -1;
        }

        StringBuilder sb = new StringBuilder();
        while (num >= 7) {
            sb.append(num % 7);
            num /= 7;
        }
        sb.append(num);

        if (negative)
            return sb.append("-").reverse().toString();
        else
            return sb.reverse().toString();
    }

    public static void main(String[] args) {
        Problem504 p = new Problem504();

        System.out.println("202".equals(p.convertToBase7(100)));
        System.out.println("-10".equals(p.convertToBase7(-7)));
        System.out.println("0".equals(p.convertToBase7(0)));
        System.out.println("5553344".equals(p.convertToBase7(685493)));
    }
}
