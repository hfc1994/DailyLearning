package Algorithm.Leetcode.pkg2000;

/**
 * Created by hfc on 2022/3/4.
 *
 * 2104. 子数组范围和
 *
 * 给你一个整数数组 nums 。nums 中，子数组的 范围 是子数组中最大元素和最小元素的差值。
 * 返回 nums 中 所有 子数组范围的 和 。
 * 子数组是数组中一个连续 非空 的元素序列。
 *
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出：4
 * 解释：nums 的 6 个子数组如下所示：
 * [1]，范围 = 最大 - 最小 = 1 - 1 = 0
 * [2]，范围 = 2 - 2 = 0
 * [3]，范围 = 3 - 3 = 0
 * [1,2]，范围 = 2 - 1 = 1
 * [2,3]，范围 = 3 - 2 = 1
 * [1,2,3]，范围 = 3 - 1 = 2
 * 所有范围的和是 0 + 0 + 0 + 1 + 1 + 2 = 4
 *
 * 示例 2：
 * 输入：nums = [1,3,3]
 * 输出：4
 * 解释：nums 的 6 个子数组如下所示：
 * [1]，范围 = 最大 - 最小 = 1 - 1 = 0
 * [3]，范围 = 3 - 3 = 0
 * [3]，范围 = 3 - 3 = 0
 * [1,3]，范围 = 3 - 1 = 2
 * [3,3]，范围 = 3 - 3 = 0
 * [1,3,3]，范围 = 3 - 1 = 2
 * 所有范围的和是 0 + 0 + 0 + 2 + 0 + 2 = 4
 *
 * 示例 3：
 * 输入：nums = [4,-2,-3,4,1]
 * 输出：59
 * 解释：nums 中所有子数组范围的和是 59
 *
 * 提示：
 * 1 <= nums.length <= 1000
 * -10^9 <= nums[i] <= 10^9
 *
 * 进阶：你可以设计一种时间复杂度为 O(n) 的解决方案吗？
 *
 */
public class Problem2104 {

    /**
     * 速度 53%
     * 内存 21%
     */
    public long subArrayRanges(int[] nums) {
        long acc = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            int max = nums[i], mini = max;
            for (int len = 1; len < nums.length - i; len++) {
                int toNum = nums[i + len];
                if (toNum > max) max = toNum;
                if (toNum < mini) mini = toNum;
                acc += (max - mini);
            }
        }

        return acc;
    }

    public static void main(String[] args) {
        Problem2104 p = new Problem2104();

        System.out.println(0 == p.subArrayRanges(new int[] {1}));
        System.out.println(4 == p.subArrayRanges(new int[] {1, 2, 3}));
        System.out.println(4 == p.subArrayRanges(new int[] {1, 3, 3}));
        System.out.println(59 == p.subArrayRanges(new int[] {4, -2, -3, 4, 1}));
        System.out.println(p.subArrayRanges(new int[] {4, -2, -3, 5, 7, -3, 8, 4, 1}));
    }

}
