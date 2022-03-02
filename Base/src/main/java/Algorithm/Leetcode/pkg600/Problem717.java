package Algorithm.Leetcode.pkg600;

/**
 * Created by hfc on 2022/2/28.
 *
 * 717. 1 比特与 2 比特字符
 *
 * 有两种特殊字符：
 * 第一种字符可以用一比特 0 表示
 * 第二种字符可以用两比特（10 或 11）表示
 * 给你一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一个一比特字符，则返回 true 。
 *
 * 示例 1:
 * 输入: bits = [1, 0, 0]
 * 输出: true
 * 解释: 唯一的解码方式是将其解析为一个两比特字符和一个一比特字符。
 * 所以最后一个字符是一比特字符。
 *
 * 示例 2:
 * 输入：bits = [1,1,1,0]
 * 输出：false
 * 解释：唯一的解码方式是将其解析为两比特字符和两比特字符。
 * 所以最后一个字符不是一比特字符。
 *
 * 提示:
 * 1 <= bits.length <= 1000
 * bits[i] 为 0 或 1
 *
 */
public class Problem717 {

    /**
     * 速度 100%
     * 内存 5%
     */
    public boolean isOneBitCharacter(int[] bits) {
        boolean endWithOneBit = false;
        for (int i=0; i<bits.length;) {
            if (bits[i] == 1) {
                i = i + 2;
                endWithOneBit = false;
            } else {
                i++;
                endWithOneBit = true;
            }
        }

        return endWithOneBit;
    }

    public static void main(String[] args) {
        Problem717 p = new Problem717();

        System.out.println(p.isOneBitCharacter(new int[]{0}));
        System.out.println(p.isOneBitCharacter(new int[]{0, 0}));
        System.out.println(!p.isOneBitCharacter(new int[]{1, 0}));
        System.out.println(p.isOneBitCharacter(new int[]{1, 0, 0}));
        System.out.println(!p.isOneBitCharacter(new int[]{1, 1, 1, 0}));
        System.out.println(!p.isOneBitCharacter(new int[]{1, 1, 1, 1}));
    }

}
