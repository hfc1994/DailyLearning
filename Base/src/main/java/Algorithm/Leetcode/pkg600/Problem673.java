package Algorithm.Leetcode.pkg600;

/**
 * Created by user-hfc on 2021/9/20.
 *
 * 673. 最长递增子序列的个数
 *
 * 给定一个未排序的整数数组，找到最长递增子序列的个数。
 *
 * 示例 1:
 * 输入: [1,3,5,4,7]
 * 输出: 2
 * 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
 *
 * 示例 2:
 * 输入: [2,2,2,2,2]
 * 输出: 5
 * 解释: 最长递增子序列的长度是1，并且存在5个子序列的长度为1，因此输出5。
 *
 * 注意: 给定的数组长度不超过 2000 并且结果一定是32位有符号整数。
 *
 */
public class Problem673 {

    // 只能计算最长子串的长度
    // 速度：93.98%，内存：90.84%
    public int findNumberOfLIS(int[] nums) {
        int len = nums.length;
        if (nums.length <= 1) return len;

        int maxLength = 1;
        int[] lengths = new int[len];
        int[] counts = new int[len];

        for (int i = 0; i < len; i++) {
            lengths[i] = 1;
            counts[i] = 1;
            // 剪枝，当向后的剩余预期长度 + 向前探的预期长度 < maxLength时，就没必要向前探了
            int limit = maxLength - (len - i + 2);
            if (limit < 0) limit = 0;
            for (int j = i - 1; j >= limit; j--) {
                if (nums[i] > nums[j]) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }

                    if (lengths[i] > maxLength)
                        maxLength = lengths[i];
                }
            }
        }

        int count = 0;
        for (int i = 0; i < lengths.length; i++) {
            if (lengths[i] == maxLength) count += counts[i];
        }

        return count;
    }

    public static void main(String[] args) {
        Problem673 p = new Problem673();

        int[] nums = new int[] {1, 3, 5, 4, 7};
        System.out.println(p.findNumberOfLIS(nums));    // 2

        nums = new int[] {1, 3, 2};
        System.out.println(p.findNumberOfLIS(nums));    // 2

        nums = new int[] {2, 2, 2, 2, 2};
        System.out.println(p.findNumberOfLIS(nums));    // 5

        nums = new int[] {7,3,5,4,7,8,12,11,9,16,10,23,32,12,16,19,7,5,19,32,19};
        System.out.println(p.findNumberOfLIS(nums));    // 4

        nums = new int[] {7,3,5,4,7,8,12,11,13,7,8,9,15,25,17,9,6};
        System.out.println(p.findNumberOfLIS(nums));    // 8

        nums = new int[] {7,3,5,4,7,8,12,11,13};
        System.out.println(p.findNumberOfLIS(nums));    // 4

        nums = new int[] {7,3,5,4,7,8,12,11,13,9,12,16,8,24,19,17,13,12,43,23,27,33,15};
        System.out.println(p.findNumberOfLIS(nums));    // 16

        nums = new int[] {1,2,3,1,2,3,1,2,3};
        System.out.println(p.findNumberOfLIS(nums));    // 10
    }

}
