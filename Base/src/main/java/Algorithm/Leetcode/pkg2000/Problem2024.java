package Algorithm.Leetcode.pkg2000;

/**
 * Created by hfc on 2022/3/29.
 *
 * 2024. 考试的最大困扰度
 *
 * 一位老师正在出一场由 n 道判断题构成的考试，每道题的答案为 true （用 'T' 表示）或者 false
 * （用 'F' 表示）。老师想增加学生对自己做出答案的不确定性，方法是 最大化 有 连续相同 结果的题数。
 * （也就是连续出现 true 或者连续出现 false）。
 * 给你一个字符串 answerKey ，其中 answerKey[i] 是第 i 个问题的正确结果。除此以外，还给你一个
 * 整数 k ，表示你能进行以下操作的最多次数：
 * - 每次操作中，将问题的正确答案改为 'T' 或者 'F' （也就是将 answerKey[i] 改为 'T' 或者 'F' ）。
 * 请你返回在不超过 k 次操作的情况下，最大 连续 'T' 或者 'F' 的数目。
 *
 * 示例 1：
 * 输入：answerKey = "TTFF", k = 2
 * 输出：4
 * 解释：我们可以将两个 'F' 都变为 'T' ，得到 answerKey = "TTTT" 。
 * 总共有四个连续的 'T' 。
 *
 * 示例 2：
 * 输入：answerKey = "TFFT", k = 1
 * 输出：3
 * 解释：我们可以将最前面的 'T' 换成 'F' ，得到 answerKey = "FFFT" 。
 * 或者，我们可以将第二个 'T' 换成 'F' ，得到 answerKey = "TFFF" 。
 * 两种情况下，都有三个连续的 'F' 。
 *
 * 示例 3：
 * 输入：answerKey = "TTFTTFTT", k = 1
 * 输出：5
 * 解释：我们可以将第一个 'F' 换成 'T' ，得到 answerKey = "TTTTTFTT" 。
 * 或者我们可以将第二个 'F' 换成 'T' ，得到 answerKey = "TTFTTTTT" 。
 * 两种情况下，都有五个连续的 'T' 。
 *  
 * 提示：
 * n == answerKey.length
 * 1 <= n <= 5 * 10^4
 * answerKey[i] 要么是 'T' ，要么是 'F'
 * 1 <= k <= n
 *
 */
public class Problem2024 {

    /**
     * 时间超限
     * 与题解的不同在于，我这种是统计区域内目标的个数
     * 优化下再次滑动时右指针的位置，应该和题解有类似的效果
     */
    public int maxConsecutiveAnswers1(String answerKey, int k) {
        int maxCnt = 0;
        int lIdx = 0, rIdx;
        char base;
        while (lIdx < answerKey.length()) {
            rIdx = lIdx;
            base = answerKey.charAt(lIdx);
            int tmpK = k;
            while (true) {
                rIdx++;
                if (rIdx >= answerKey.length()) {
                    //
                } else if (answerKey.charAt(rIdx) == base) {
                    continue;
                } else if (tmpK > 0) {
                    tmpK--;
                    continue;
                } else {
                    //
                }
                int count = rIdx - lIdx;
                if (tmpK > 0) {
                    count = Math.min(answerKey.length(), count + tmpK);
                }
                if (count > maxCnt) maxCnt = count;
                break;
            }

            while ((++lIdx) < answerKey.length() && answerKey.charAt(lIdx) == base) {}
        }
        return maxCnt;
    }

    /**
     * 来自题解
     * 想法是统计区域内非目标值的多少，与阈值 k 做比较
     */
    public int maxConsecutiveAnswers(String answerKey, int k) {
        return Math.max(this.maxCount(answerKey, 'T', k), this.maxCount(answerKey, 'F', k));
    }

    // 统计不是目标值的个数
    private int maxCount(String str, char target, int k) {
        int count = 0;
        for (int left = 0, right = 0, sum = 0; right < str.length(); right++) {
            sum += str.charAt(right) == target ? 0 : 1;
            if (sum > k) {
                sum -= str.charAt(left++) == target ? 0 : 1;
            }

            count = Math.max(count, right - left + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        Problem2024 p = new Problem2024();

        System.out.println(4 == p.maxConsecutiveAnswers("TTFF", 2));
        System.out.println(3 == p.maxConsecutiveAnswers("TFFT", 1));
        System.out.println(5 == p.maxConsecutiveAnswers("TTFTTFTT", 1));
        System.out.println(10 == p.maxConsecutiveAnswers("TTFFTTFFFFTTTFTFFTTFTFTTFFFTFTFFFTTTFFTFFT", 3));
        System.out.println(9 == p.maxConsecutiveAnswers("TTFFTTFFFFTTTFTFFTTFTFTTFFFTFTFFFTTTFFTFFT", 2));
        System.out.println(12 == p.maxConsecutiveAnswers("TTFFTTFFFFTTTFTFFTTFTFTTFFFTFTFFFTTTFFTFFT", 4));
        System.out.println(8 == p.maxConsecutiveAnswers("FFFTTFTTFT", 3));
    }
}
