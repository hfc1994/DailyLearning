package Algorithm.Leetcode.within1000;

import Algorithm.Leetcode.LeetcodeUtil;

/**
 * Created by user-hfc on 2021/8/31.
 *
 * 977. 有序数组的平方
 *
 * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
 *
 * 示例 1：
 * 输入：nums = [-4,-1,0,3,10]
 * 输出：[0,1,9,16,100]
 * 解释：平方后，数组变为 [16,1,0,9,100]
 * 排序后，数组变为 [0,1,9,16,100]
 *
 * 示例 2：
 * 输入：nums = [-7,-3,2,3,11]
 * 输出：[4,9,9,49,121]
 *
 * 提示：
 * 1 <= nums.length <= 10^4
 * -10^4 <= nums[i] <= 10^4
 * nums 已按 非递减顺序 排序
 *
 * 进阶：
 * 请你设计时间复杂度为 O(n) 的算法解决本问题
 *
 */
public class Problem977 {

    public int[] sortedSquares(int[] nums) {
        if (nums.length == 1 || nums[0] >= 0) {
            for (int i=0; i<nums.length; i++) {
                nums[i] = nums[i] * nums[i];
            }
            return nums;
        }

        int leftIdx = 0, rightIdx = nums.length - 1, idx = rightIdx;
        int left, right;
        int[] dst = new int[nums.length];
        while (leftIdx <= rightIdx) {
            left = nums[leftIdx] * nums[leftIdx];
            right = nums[rightIdx] * nums[rightIdx];
            if (right >= left) {
                dst[idx--] = right;
                rightIdx--;
            } else {
                dst[idx--] = left;
                leftIdx++;
            }
        }

        return dst;
    }

    public static void main(String[] args) {
        Problem977 p = new Problem977();

        LeetcodeUtil.printArray(p.sortedSquares(new int[] {-4,-1,0,3,10}));    // [0,1,9,16,100]
        LeetcodeUtil.printArray(p.sortedSquares(new int[] {-7,-3,2,3,11}));    // [4,9,9,49,121]
    }

}
