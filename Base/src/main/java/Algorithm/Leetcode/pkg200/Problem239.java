package Algorithm.Leetcode.pkg200;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.PriorityQueue;

/**
 * Created by hfc on 2022/1/23.
 *
 * 239. 滑动窗口最大值
 *
 * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
 * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 返回滑动窗口中的最大值。
 *
 * 示例 1：
 * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 * 输出：[3,3,5,5,6,7]
 * 解释：
 * 滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * 示例 2：
 * 输入：nums = [1], k = 1
 * 输出：[1]
 *
 * 示例 3：
 * 输入：nums = [1,-1], k = 1
 * 输出：[1,-1]
 *
 * 示例 4：
 * 输入：nums = [9,11], k = 2
 * 输出：[11]
 *
 * 示例 5：
 * 输入：nums = [4,-2], k = 2
 * 输出：[4]
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 *
 */
public class Problem239 {

    /**
     * nums.length = 100000 和 k = 50000 时时间超限
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        if (nums.length == 1) {
            result[0] = nums[0];
            return result;
        }

        int idx = 0;
        int count = 0;
        int max = Integer.MIN_VALUE;
        int maxIdx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIdx = i;
            } else if (count >= k && i == maxIdx + k) {
                max = Integer.MIN_VALUE;
                for (int j = i - k + 1; j <= i; j++) {
                    if (nums[j] > max) {
                        max = nums[j];
                        maxIdx = j;
                    }
                }
            }

            count++;
            if (count >= k) {
                result[idx++] = max;
            }
        }

        return result;
    }

    /**
     * nums.length = 100000 和 k = 50000 时时间超限
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        if (nums.length == 1) {
            result[0] = nums[0];
            return result;
        }

        int idx = 0;
        int count = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>(k, (i1, i2) -> i2 -i1);
        for (int i = 0; i < nums.length; i++) {
            if (count >= k) {
                queue.remove(nums[i - k]);
            }
            count++;
            queue.add(nums[i]);

            if (count >= k) {
                result[idx++] = queue.peek();
            }
        }

        return result;
    }

    /**
     * 速度 8%
     * 内存 24%
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        if (nums.length == 1) {
            result[0] = nums[0];
            return result;
        }

        int idx = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>(k, (iA1, iA2) -> iA2[0] - iA1[0]);
        for (int i = 0; i < nums.length; i++) {
            // 0: 数值，1：数值的索引
            queue.add(new int[] { nums[i], i });

            if (i >= k - 1) {
                // 延迟删除，要用的时候再判断是否已经在滑动窗口外了
                while (queue.peek()[1] < i - k + 1) {
                    queue.poll();
                }
                result[idx++] = queue.peek()[0];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Problem239 p = new Problem239();

        System.out.println(LeetcodeUtil.equalsOfArray(p.maxSlidingWindow(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 }, 3), new int[] { 3, 3, 5, 5, 6, 7 }));
        System.out.println(LeetcodeUtil.equalsOfArray(p.maxSlidingWindow(new int[] { 1, -1 }, 1), new int[] { 1, -1 }));
        System.out.println(LeetcodeUtil.equalsOfArray(p.maxSlidingWindow(new int[] { 9, 11 }, 2), new int[] { 11 }));
        System.out.println(LeetcodeUtil.equalsOfArray(p.maxSlidingWindow(new int[] { 4, -2 }, 2), new int[] { 4 }));
    }

}
