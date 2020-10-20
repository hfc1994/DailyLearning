package Algorithm.Leetcode;

/**
 * Created by hfc on 2020/9/26.
 *
 * 300. 最长上升子序列
 *
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 *
 * 示例:
 * 输入: [10,9,2,5,3,7,101,18]
 * 输出: 4
 * 解释: 最长的上升子序列是 [2,3,7,101]，它的长度是 4。
 *
 * 说明:
 * 可能会有多种最长上升子序列的组合，你只需要输出对应的长度即可。
 * 你算法的时间复杂度应该为 O(n2) 。
 * 进阶: 你能将算法的时间复杂度降低到 O(n log n) 吗?
 *
 */
public class Problem300 {

    public int lengthOfLIS(int[] nums) {
//        return solution1(nums);
        return solution2(nums);
    }

    // O(n^2)
    public int solution1(int[] nums) {
        int[] dp = new int[nums.length];
        int max = 0;

        for (int i=0; i<nums.length; i++) {
            dp[i] = 1;
            for (int j=0; j<i; j++) {
                if (nums[i] > nums[j] && dp[j] + 1 > dp[i])
                    dp[i] = dp[j] + 1;
            }
            max = dp[i] > max ? dp[i] : max;
        }

        return max;
    }

    /**
     * O(n log n)
     * 官方题解，贪心 + 二分查找：
     *
     * 以输入序列 [0, 8, 4, 12, 2]为例：
     * 第一步插入 0， d = [0]；
     * 第二步插入 8， d = [0, 8]；
     * 第三步插入 4， d = [0, 4]；
     * 第四步插入 12，d = [0, 4, 12]；
     * 第五步插入 2， d = [0, 2, 12]。 // 当数据量足够多时，此处插入的2可能会与后面的数据构成更长的串
     */
    public int solution2(int[] nums) {
        if (nums.length == 0)
            return 0;

        int[] LIS = new int[nums.length];
        LIS[0] = nums[0];
        int idx = 0, begin, end, mid, target;

        for (int i=1; i<nums.length; i++) {
            if (nums[i] > LIS[idx])
                LIS[++idx] = nums[i];
            else {
                begin = 0;
                end = idx;
                target = nums[i];
                // 找到第一个大于target的值位置
                while (end > begin) {
                    mid = (begin + end) / 2;
                    if (LIS[mid] < target) begin = mid + 1;
                    else end = mid;
                }
                LIS[begin] = nums[i];
            }
        }

        return idx+1;
    }

    public static void main(String[] args) {
        Problem300 p = new Problem300();

        System.out.println(p.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));   // 4
        System.out.println(p.lengthOfLIS(new int[]{0}));    // 1
        System.out.println(p.lengthOfLIS(new int[]{10,9,2,5,3,3,3,3,7,9,10,30,2,17,40,21,11,32,12,9,101,18}));  // 9
        System.out.println(p.lengthOfLIS(new int[]{-2, -1}));  // 2
        System.out.println(p.lengthOfLIS(new int[]{2,15,3,7,8,6,18}));  // 5
    }
}
