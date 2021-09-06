package Algorithm.Leetcode.pkg400;

/**
 * Created by user-hfc on 2021/9/5.
 *
 * 567. 字符串的排列
 *
 * 给你两个字符串 s1 和 s2 ，写一个函数来判断 s2 是否包含 s1 的排列。
 * 换句话说，s1 的排列之一是 s2 的 子串 。
 *
 * 示例 1：
 * 输入：s1 = "ab" s2 = "eidbaooo"
 * 输出：true
 * 解释：s2 包含 s1 的排列之一 ("ba").
 *
 * 示例 2：
 * 输入：s1= "ab" s2 = "eidboaoo"
 * 输出：false
 *
 * 提示：
 * 1 <= s1.length, s2.length <= 10^4
 * s1 和 s2 仅包含小写字母
 *
 */
public class Problem567 {

    public boolean checkInclusion_1(String s1, String s2) {
        if (s2.length() < s1.length())
            return false;

        // 只有26个小写字母
        int flag = 0;
        // 有重复字母时用于计数
        int[] counter = new int[26];
        char base = 'a';
        int diff;
        for (int i = 0; i < s1.length(); i++) {
            diff = s1.charAt(i) - base;
            flag |= (1 << diff);    // 在字符指定的位上设置1
            if (counter[diff] == 0)
                counter[diff] += 2; // 最基础的1用于表示这位上的字符是子串中存在的，这个1不会被清除
            else
                counter[diff]++;
        }

        int left = 0, right = 0;
        while (right < s2.length()) {
            diff = s2.charAt(right) - base;
            if (counter[diff] == 0) {   // s2的字符在s1中没有
            } else if (counter[diff] == 1) {    // 重复出现的，且超量了
                while (counter[diff] == 1) {
                    diff = s2.charAt(left) - base;
                    if (counter[diff] >= 1) {
                        flag |= (1 << diff);    // 恢复一位，即把变成0的位恢复为1
                        counter[diff]++;
                    }
                    left++;
                }
                continue;
            } else {
                counter[diff]--;
                if (counter[diff] == 1)
                    flag &= ~(1 << diff);  // 把对应索引位置上的1变为0，表示该字符已经达到出现次数上限
            }

            if (right - left + 1 >= s1.length()) {   // 窗口长度到了阈值
                if (flag == 0){
                    return true;
                } else {
                    diff = s2.charAt(left) - base;
                    if (counter[diff] >= 1) {
                        flag |= (1 << diff);    // 恢复一位
                        counter[diff]++;
                    }
                    left++;
                }
            }
            right++;
        }

        return false;
    }

    // 借鉴自leetcode评论区，速度远不如上面那个
    public boolean checkInclusion(String s1, String s2) {
        if (s2.length() < s1.length())
            return false;

        int targetTotal = 0;
        for (int i = 0; i < s1.length(); i++) {
            targetTotal += s1.charAt(i) * s1.charAt(i);
        }

        int left = 0, right = s1.length() - 1;
        int temp;
        do {
            temp = 0;
            for (int i = left; i <= right; i++) {
                temp += s2.charAt(i) * s2.charAt(i);
            }

            if (temp == targetTotal) {
                return true;
            } else {
                right++;
                left++;
            }
        } while (right < s2.length());
        return false;
    }

    public static void main(String[] args) {
        Problem567 p = new Problem567();

        System.out.println((int) 'a');  // 97
        System.out.println((int) 'z');  // 122

        System.out.println(p.checkInclusion("ab", "eidbaooo")); // true
        System.out.println(p.checkInclusion("ab", "eidboaoo")); // false
        System.out.println(p.checkInclusion("ab", "ba")); // true
        System.out.println(p.checkInclusion("a", "a")); // true
        System.out.println(p.checkInclusion("a", "b")); // false
        System.out.println(p.checkInclusion("adc", "dcda")); // true

        System.out.println(p.checkInclusion("trinitrophenylmethylnitramine",
                "dinitrophenylhydrazinetrinitrophenylmethylnitramine")); // true
    }

}
