package Algorithm.Leetcode.pkg600;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2021/9/12.
 *
 * 784. 字母大小写全排列
 *
 * 给定一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。
 * 返回所有可能得到的字符串集合。
 *
 * 示例：
 * 输入：S = "a1b2"
 * 输出：["a1b2", "a1B2", "A1b2", "A1B2"]
 *
 * 输入：S = "3z4"
 * 输出：["3z4", "3Z4"]
 *
 * 输入：S = "12345"
 * 输出：["12345"]
 *
 * 提示：
 * S 的长度不超过12。
 * S 仅由数字和字母组成。
 *
 */
public class Problem784 {

    public List<String> letterCasePermutation(String s) {
        if (null == s || "".equals(s.trim()))
            return Collections.emptyList();

        List<String> list = new LinkedList<>();
        backTrack(list, s.toCharArray(), 0);
        // 全是数字
        if (list.size() == 0) {
            list.add(s);
        }
        return list;
    }

    private void backTrack(List<String> list, char[] cs, int start) {
        int index;
        for (index = start; index < cs.length; index++) {
            char c = cs[index];
            char nc;
            if (c >= 'a' && c <= 'z') {
                nc = (char) (c - 32);
            } else if (c >= 'A' && c <= 'Z') {
                nc = (char) (c + 32);
            } else {
                continue;
            }

            backTrack(list, cs, index + 1);
            cs[index] = nc;
            backTrack(list, cs, index + 1);
            cs[index] = c;
            break;
        }

        if (index == cs.length)
            list.add(new String(cs));
    }

    public static void main(String[] args) {
        Problem784 p = new Problem784();

        String s = "a1b2";
        LeetcodeUtil.printList(p.letterCasePermutation(s));

        s = "3z4";
        LeetcodeUtil.printList(p.letterCasePermutation(s));

        s = "12345";
        LeetcodeUtil.printList(p.letterCasePermutation(s));

        // 题目要求没说是否要考虑重复的字母（忽略大小写）的问题，实际leetcode官方也会有重复输出
        s = "a1A2";
        LeetcodeUtil.printList(p.letterCasePermutation(s));
    }

}
