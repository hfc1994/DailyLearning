package Algorithm.Leetcode.pkg800;

/**
 * Created by hfc on 2022/2/27.
 *
 * 917. 仅仅反转字母
 *
 * 给你一个字符串 s ，根据下述规则反转字符串：
 * 所有非英文字母保留在原有位置。
 * 所有英文字母（小写或大写）位置反转。
 * 返回反转后的 s 。
 *
 * 示例 1：
 * 输入：s = "ab-cd"
 * 输出："dc-ba"
 *
 * 示例 2：
 * 输入：s = "a-bC-dEf-ghIj"
 * 输出："j-Ih-gfE-dCba"
 *
 * 示例 3：
 * 输入：s = "Test1ng-Leet=code-Q!"
 * 输出："Qedo1ct-eeLg=ntse-T!"
 *  
 * 提示
 * 1 <= s.length <= 100
 * s 仅由 ASCII 值在范围 [33, 122] 的字符组成
 * s 不含 '\"' 或 '\\'
 *
 */
public class Problem917 {

    /**
     * 速度 100%
     * 内存 68%
     */
    public String reverseOnlyLetters(String s) {
        char[] cs = s.toCharArray();
        int left = 0, right = cs.length - 1;

        while (left < right) {
            while (!this.isAlphabet(cs[left]) && left < right) left++;
            while (!this.isAlphabet(cs[right]) && right > left) right--;
            if (left >= right) break;

            char tmp = cs[left];
            cs[left] = cs[right];
            cs[right] = tmp;

            left++;
            right--;
        }

        return new String(cs);
    }

    private boolean isAlphabet(char c) {
        return (c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z');
    }

    public static void main(String[] args) {
        Problem917 p = new Problem917();

        System.out.println("dc-ba".equals(p.reverseOnlyLetters("ab-cd")));
        System.out.println("j-Ih-gfE-dCba".equals(p.reverseOnlyLetters("a-bC-dEf-ghIj")));
        System.out.println("Qedo1ct-eeLg=ntse-T!".equals(p.reverseOnlyLetters("Test1ng-Leet=code-Q!")));
        System.out.println("-".equals(p.reverseOnlyLetters("-")));
        System.out.println("a".equals(p.reverseOnlyLetters("a")));
        System.out.println("a-".equals(p.reverseOnlyLetters("a-")));
        System.out.println("-a-".equals(p.reverseOnlyLetters("-a-")));
        System.out.println("b-a".equals(p.reverseOnlyLetters("a-b")));
    }

}
