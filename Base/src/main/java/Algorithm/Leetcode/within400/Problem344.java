package Algorithm.Leetcode.within400;

import Algorithm.Leetcode.LeetcodeUtil;

/**
 * Created by user-hfc on 2021/9/2.
 *
 * 344. 反转字符串
 *
 * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
 * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
 * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
 *
 * 示例 1：
 * 输入：["h","e","l","l","o"]
 * 输出：["o","l","l","e","h"]
 *
 * 示例 2：
 * 输入：["H","a","n","n","a","h"]
 * 输出：["h","a","n","n","a","H"]
 *
 */
public class Problem344 {

    public void reverseString(char[] s) {
        if (s.length <= 1) {
            return;
        }

        int left = 0, right = s.length - 1;
        char ch;
        while (left < right) {
            ch = s[left];
            s[left] = s[right];
            s[right] = ch;

            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        Problem344 p = new Problem344();

        char[] chars = new char[] {'h','e','l','l','o'};
        p.reverseString(chars);
        LeetcodeUtil.printArray(chars); // ["o","l","l","e","h"]

        chars = new char[] {'H','a','n','n','a','h'};
        p.reverseString(chars);
        LeetcodeUtil.printArray(chars); // ["h","a","n","n","a","H"]

        chars = new char[] {'H'};
        p.reverseString(chars);
        LeetcodeUtil.printArray(chars); // ["H"]

        chars = new char[] {'H','h'};
        p.reverseString(chars);
        LeetcodeUtil.printArray(chars); // ["h","H"]

        chars = new char[] {'H','h', 'a'};
        p.reverseString(chars);
        LeetcodeUtil.printArray(chars); // ["a","h","H"]
    }
}
