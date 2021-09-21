package Algorithm.Leetcode.pkg0;

/**
 * Created by user-hfc on 2021/9/21.
 *
 * 58. 最后一个单词的长度
 *
 * 给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。
 * 返回字符串中最后一个单词的长度。
 * 单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。
 *
 * 示例 1：
 * 输入：s = "Hello World"
 * 输出：5
 *
 * 示例 2：
 * 输入：s = "   fly me   to   the moon  "
 * 输出：4
 *
 * 示例 3：
 * 输入：s = "luffy is still joyboy"
 * 输出：6
 *
 * 提示：
 * 1 <= s.length <= 10^4
 * s 仅有英文字母和空格 ' ' 组成
 * s 中至少存在一个单词
 *
 */
public class Problem58 {

    // 速度：37.87%，内存：32.35%
    public int lengthOfLastWord_2(String s) {
        String[] strs = s.split(" ");

        return strs[strs.length - 1].length();
    }

    // 速度：100%，内存：80.48%
    public int lengthOfLastWord(String s) {
        int count = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                if (count > 0)
                    return count;
            } else {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Problem58 p = new Problem58();

        System.out.println(p.lengthOfLastWord("Hello World"));  // 5
        System.out.println(p.lengthOfLastWord("   fly me   to   the moon  "));  // 4
        System.out.println(p.lengthOfLastWord("luffy is still joyboy"));  // 6
        System.out.println(p.lengthOfLastWord("Today is a nice day"));  // 3
        System.out.println(p.lengthOfLastWord("word"));  // 4
    }

}
