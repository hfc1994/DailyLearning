package Algorithm.Leetcode.pkg400;

/**
 * Created by hfc on 2020/8/30.
 *
 * 557. 反转字符串中的单词 III
 *
 * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
 *
 * 示例：
 * 输入："Let's take LeetCode contest"
 * 输出："s'teL ekat edoCteeL tsetnoc"
 *  
 * 提示：
 * 在字符串中，每个单词由<b>单个</b>空格分隔，并且字符串中<b>不会有任何额外</b>的空格。
 *
 */
public class Problem557 {

    public String reverseWords_1(String s) {
        char blank = ' ';
        StringBuilder sb = new StringBuilder();
        int length = s.length();
        for (int begin=0, end=0; end <= length; end++) {
            if (end == length || s.charAt(end) == blank) {
                for (int i=end-1; i>=begin; i--)
                    sb.append(s.charAt(i));

                if (end != length)
                    sb.append(s.charAt(end));   // 加空格
                begin = end + 1;
            }
        }

        return sb.toString();
    }

    public String reverseWords(String s) {
        char blank = ' ';
        char exchage;
        char[] chars = s.toCharArray();

        int left = 0, right = 0;
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] == blank && i != 0) || i == chars.length - 1) {
                right = (i == chars.length - 1) ? i : i - 1;
                while (left < right) {
                    exchage = chars[left];
                    chars[left] = chars[right];
                    chars[right] = exchage;

                    left++;
                    right--;
                }

                if (i < chars.length - 1) {
                    left = i + 1;
                }
            }
        }

        return new String(chars);
    }

    public static void main(String[] args) {
        Problem557 p = new Problem557();
        System.out.println("'" + p.reverseWords("") + "'");
        System.out.println("'" + p.reverseWords(" ") + "'");
        System.out.println("'" + p.reverseWords("qwer") + "'");
        System.out.println("'" + p.reverseWords("qwer asdf") + "'");
        System.out.println("'" + p.reverseWords("qwer asdf zxcv") + "'");
        System.out.println("'" + p.reverseWords("qwer asdf zxcv qaz") + "'");
        System.out.println("'" + p.reverseWords("qwer edc asd qaz") + "'");
        System.out.println("'" + p.reverseWords("Let's take LeetCode contest") + "'");
    }
}
