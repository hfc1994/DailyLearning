package Algorithm.Leetcode.pkg0;

import Algorithm.Leetcode.LeetcodeUtil;

/**
 * Created by user-hfc on 2021/9/1.
 *
 * 189. 旋转数组
 *
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 *
 * 进阶：
 * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
 * 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
 *
 * 示例 1:
 * 输入: nums = [1,2,3,4,5,6,7], k = 3
 * 输出: [5,6,7,1,2,3,4]
 * 解释:
 * 向右旋转 1 步: [7,1,2,3,4,5,6]
 * 向右旋转 2 步: [6,7,1,2,3,4,5]
 * 向右旋转 3 步: [5,6,7,1,2,3,4]
 *
 * 示例 2:
 * 输入：nums = [-1,-100,3,99], k = 2
 * 输出：[3,99,-1,-100]
 * 解释:
 * 向右旋转 1 步: [99,-1,-100,3]
 * 向右旋转 2 步: [3,99,-1,-100]
 *
 * 提示：
 * 1 <= nums.length <= 2 * 10^4
 * -2^31 <= nums[i] <= 2^31 - 1
 * 0 <= k <= 10^5
 *
 */
public class Problem189 {

    // 来自leetcode评论区，双重循环法
    public void rotate_2(int[] nums, int k) {
        if (nums.length == 0)
            return;

        int n = nums.length;
        k = k % n;
        int temp;
        for (int i = 0; i < k; i++) {
            temp = nums[n - 1];
            for (int j = n - 1; j > 0; j--) {
                nums[j] = nums[j - 1];
            }
            nums[0] = temp;
        }
    }

    // 速度和内存占用略差
    public void rotate_1(int[] nums, int k) {
        if (nums.length == 0)
            return;

        int count = 0;
        int exchange = nums[0], store;
        int lIdx = 0, rIdx, beginIdx = 0;
        while (count < nums.length) {
            rIdx = (lIdx + k) % nums.length;
            store = nums[rIdx];
            nums[rIdx] = exchange;
            lIdx = rIdx;
            exchange = store;
            count++;

            if (lIdx == beginIdx && lIdx < nums.length - 1) { // k能整除nums.length，会导致一圈后回到原地
                beginIdx = ++lIdx;
                exchange = nums[lIdx];
            }
        }
    }

    public void rotate(int[] nums, int k) {
        if (nums.length == 0)
            return;

        int[] newNum = new int[nums.length];
        System.arraycopy(nums, 0, newNum, 0, nums.length);
        int rIdx;
        for (int lIdx = 0; lIdx < nums.length; lIdx++) {
            rIdx = (lIdx + k) % nums.length;
            nums[rIdx] = newNum[lIdx];
        }
    }

    public static void main(String[] args) {
        Problem189 p = new Problem189();

        int[] nums = new int[] {1,2,3,4,5,6,7};
        p.rotate(nums, 3);
        LeetcodeUtil.printArray(nums);  // [5,6,7,1,2,3,4]

        nums = new int[] {-1,-100,3,99};
        p.rotate(nums, 2);
        LeetcodeUtil.printArray(nums);  // [3,99,-1,-100]

        nums = new int[] {1,2,3,4,5,6,7,8,9};
        p.rotate(nums, 3);
        LeetcodeUtil.printArray(nums);  // [7,8,9,1,2,3,4,5,6]

        nums = new int[] {1,2,3,4,5,6,7,8};
        p.rotate(nums, 4);
        LeetcodeUtil.printArray(nums);  // [5,6,7,8,1,2,3,4]

        nums = new int[] {1,2,3,4};
        p.rotate(nums, 4);
        LeetcodeUtil.printArray(nums);  // [1,2,3,4]

    }

}
