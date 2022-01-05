package Algorithm.Leetcode.pkg400;

/**
 * Created by hfc on 2022/1/4.
 *
 * 520. 检测大写字母
 *
 * 我们定义，在以下情况时，单词的大写用法是正确的：
 * 1、全部字母都是大写，比如 "USA" 。
 * 2、单词中所有字母都不是大写，比如 "leetcode" 。
 * 3、如果单词不只含有一个字母，只有首字母大写，比如"Google" 。
 * 给你一个字符串 word 。如果大写用法正确，返回 true ；否则，返回 false 。
 *
 * 示例 1：
 * 输入：word = "USA"
 * 输出：true
 *
 * 示例 2：
 * 输入：word = "FlaG"
 * 输出：false
 *
 * 提示：
 * 1 <= word.length <= 100
 * word 由小写和大写英文字母组成
 *
 */
public class Problem520 {

    public boolean detectCapitalUse(String word) {
        if (word.length() == 1) return true;

        char charZ = 'Z';
        boolean startUpper = word.charAt(0) <= charZ;
//        if (startUpper) {
//            boolean flag = word.charAt(1) <= charZ;
//            for (int i = 1; i < word.length(); i++) {
//                if ((word.charAt(i) <= charZ) != flag) {
//                    return false;
//                }
//            }
//        } else {
//            for (int i = 1; i < word.length(); i++) {
//                if (word.charAt(i) <= charZ) {
//                    return false;
//                }
//            }
//        }

        // 由上文注释处推导
        boolean caseFlag = startUpper ? word.charAt(1) <= charZ : false;
        for (int i = 1; i < word.length(); i++) {
            if ((word.charAt(i) <= charZ) != caseFlag) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Problem520 p = new Problem520();

        System.out.println(p.detectCapitalUse("A"));        // true
        System.out.println(p.detectCapitalUse("b"));        // true
        System.out.println(p.detectCapitalUse("USA"));      // true
        System.out.println(p.detectCapitalUse("FLaG"));     // false
        System.out.println(p.detectCapitalUse("Google"));   // true
        System.out.println(p.detectCapitalUse("leetcode")); // true
    }

}
