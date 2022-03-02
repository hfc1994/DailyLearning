package Algorithm.Leetcode.pkg400;

/**
 * Created by hfc on 2022/2/25.
 *
 * 537. 复数乘法
 *
 * 复数 可以用字符串表示，遵循 "实部+虚部i" 的形式，并满足下述条件：
 * 实部 是一个整数，取值范围是 [-100, 100]
 * 虚部 也是一个整数，取值范围是 [-100, 100]
 * i2 == -1
 * 给你两个字符串表示的复数 num1 和 num2 ，请你遵循复数表示形式，返回表示它们乘积的字符串。
 *
 * 示例 1：
 * 输入：num1 = "1+1i", num2 = "1+1i"
 * 输出："0+2i"
 * 解释：(1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i ，你需要将它转换为 0+2i 的形式。
 *
 * 示例 2：
 * 输入：num1 = "1+-1i", num2 = "1+-1i"
 * 输出："0+-2i"
 * 解释：(1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i ，你需要将它转换为 0+-2i 的形式。
 *
 * 提示：
 * num1 和 num2 都是有效的复数表示。
 *
 */
public class Problem537 {

    /**
     * 速度 85%
     * 内存 16%
     */
    public String complexNumberMultiply(String num1, String num2) {
        int[] nums1 = this.splitNum(num1);
        int[] nums2 = this.splitNum(num2);

        int left = nums1[0] * nums2[0];
        int mid = nums1[0] * nums2[1] + nums1[1] * nums2[0];
        int right = nums1[1] * nums2[1];

        return left + right * (-1) + "+" + mid + "i";
    }

    private int[] splitNum(String strNum) {
        int[] nums = new int[2];
        String[] strs = strNum.split("\\+");
        nums[0] = Integer.parseInt(strs[0]);
        nums[1] = Integer.parseInt(strs[1].substring(0, strs[1].length() - 1));
        return nums;
    }

    public static void main(String[] args) {
        Problem537 p = new Problem537();

        System.out.println("0+2i".equals(p.complexNumberMultiply("1+1i", "1+1i")));
        System.out.println("0+-2i".equals(p.complexNumberMultiply("1+-1i", "1+-1i")));
        System.out.println("-1+-1i".equals(p.complexNumberMultiply("0+-1i", "1+-1i")));
        System.out.println("-1+0i".equals(p.complexNumberMultiply("0+-1i", "0+-1i")));
        System.out.println("0+0i".equals(p.complexNumberMultiply("0+0i", "0+-1i")));
        System.out.println("0+0i".equals(p.complexNumberMultiply("0+0i", "0+0i")));
    }
}
