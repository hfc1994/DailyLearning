package Algorithm.Leetcode.pkg600;

/**
 * Created by hfc on 2022/1/13.
 *
 * 747. 至少是其他数字两倍的最大数
 *
 * 给你一个整数数组 nums ，其中总是存在 唯一的 一个最大整数 。
 * 请你找出数组中的最大元素并检查它是否 至少是数组中每个其他数字的两倍 。
 * 如果是，则返回 最大元素的下标 ，否则返回 -1 。
 *
 * 示例 1：
 * 输入：nums = [3,6,1,0]
 * 输出：1
 * 解释：6 是最大的整数，对于数组中的其他整数，6 大于数组中其他元素的两倍。6 的下标是 1 ，所以返回 1 。
 *
 * 示例 2：
 * 输入：nums = [1,2,3,4]
 * 输出：-1
 * 解释：4 没有超过 3 的两倍大，所以返回 -1 。
 *
 * 示例 3：
 * 输入：nums = [1]
 * 输出：0
 * 解释：因为不存在其他数字，所以认为现有数字 1 至少是其他数字的两倍。
 *
 * 提示：
 * 1 <= nums.length <= 50
 * 0 <= nums[i] <= 100
 * nums 中的最大元素是唯一的
 *
 */
public class Problem747 {

    /**
     * 速度 100%
     * 内存 27%
     */
    public int dominantIndex1(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int maxIdx = nums[1] > nums[0] ? 1 : 0;
        boolean flag = true;

        for (int i = 0; i < nums.length;) {
            if (i == maxIdx) {
                i++;
                continue;
            }

            if (nums[i] != 0 && nums[maxIdx] / nums[i] < 2) {
                flag = false;
                if (nums[maxIdx] >= nums[i]) {
                    int j;
                    for (j = maxIdx + 1; j < nums.length; j++) {
                        if (nums[i] == 0 || nums[j] / nums[maxIdx] >= 2) {
                            maxIdx = j;
                            break;
                        }
                    }

                    if (j == nums.length) {
                        break;
                    }
                } else {
                    int tmp = maxIdx;
                    maxIdx = i;
                    i = tmp;
                }
            } else {
                flag = true;
                i++;
            }

        }

        return flag ? maxIdx : -1;
    }

    /**
     * 速度 100%
     * 内存 67%
     */
    public int dominantIndex2(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int max = nums[0];
        int maxIdx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIdx = i;
            }
        }

        for (int n : nums) {
            if (n == max || n == 0) continue;
            if (max / n < 2) {
                return -1;
            }
        }
        return maxIdx;
    }

    public int dominantIndex(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }

        int maxIdx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        for (int n : nums) {
            if (n == nums[maxIdx] || n == 0) continue;
            if (nums[maxIdx] / n < 2) {
                return -1;
            }
        }
        return maxIdx;
    }

    public static void main(String[] args) {
        Problem747 p = new Problem747();

        System.out.println(p.dominantIndex(new int[] { 0, 0, 1, 0 }) == 2);
        System.out.println(p.dominantIndex(new int[] { 0, 2, 0, 3 }) == -1);
        System.out.println(p.dominantIndex(new int[] { 0, 1, 1, 2 }) == 3);
        System.out.println(p.dominantIndex(new int[] { 3, 6, 1, 0 }) == 1);
        System.out.println(p.dominantIndex(new int[] { 1, 2, 3, 4 }) == -1);
        System.out.println(p.dominantIndex(new int[] { 1, 2, 3, 6 }) == 3);
        System.out.println(p.dominantIndex(new int[] { 1 }) == 0);
        System.out.println(p.dominantIndex(new int[] { 6, 3, 12, 1 }) == 2);
        System.out.println(p.dominantIndex(new int[] { 6, 3, 1, 5, 7, 4 }) == -1);
        System.out.println(p.dominantIndex(new int[] { 6, 3, 1, 7, 4, 5 }) == -1);
        System.out.println(p.dominantIndex(new int[] { 6, 3, 1, 7, 4, 5, 14 }) == 6);
        System.out.println(p.dominantIndex(new int[] { 6, 3, 1, 5, 10, 4 }) == -1);
        System.out.println(p.dominantIndex(new int[] { 6, 3, 1, 5, 7, 14 }) == 5);
    }
}
