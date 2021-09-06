package Algorithm.Leetcode.pkg0;

/**
 * Created by user-hfc on 2021/8/31.
 *
 * 35. 搜索插入位置
 *
 * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，
 * 返回它将会被按顺序插入的位置。
 * 请必须使用时间复杂度为 O(log n) 的算法。
 *
 * 示例 1:
 * 输入: nums = [1,3,5,6], target = 5
 * 输出: 2
 *
 * 示例 2:
 * 输入: nums = [1,3,5,6], target = 2
 * 输出: 1
 *
 * 示例 3:
 * 输入: nums = [1,3,5,6], target = 7
 * 输出: 4
 *
 * 示例 4:
 * 输入: nums = [1,3,5,6], target = 0
 * 输出: 0
 *
 * 示例 5:
 * 输入: nums = [1], target = 0
 * 输出: 0
 *
 * 提示:
 * 1 <= nums.length <= 104
 * -104 <= nums[i] <= 104
 * nums 为无重复元素的升序排列数组
 * -104 <= target <= 104
 *
 */
public class Problem35 {

    public int searchInsert(int[] nums, int target) {
        int begin = 0, end = nums.length - 1, mid;
        int temp;
        while (begin <= end) {
            mid = (begin + end) / 2;
            temp = nums[mid];
            if (temp == target) {
                return mid;
            } else if (temp > target) {
                end = mid - 1;
            } else {
                begin = mid + 1;
            }
        }
        return begin;
    }

    public static void main(String[] args) {
        Problem35 p = new Problem35();

        System.out.println(p.searchInsert(new int[] {1,3,5,6}, 5)); // 2
        System.out.println(p.searchInsert(new int[] {1,3,5,6}, 2)); // 1
        System.out.println(p.searchInsert(new int[] {1,3,5,6}, 7)); // 4
        System.out.println(p.searchInsert(new int[] {1,3,5,6}, 0)); // 0
        System.out.println(p.searchInsert(new int[] {1}, 0)); // 0
        System.out.println(p.searchInsert(new int[] {1}, 1)); // 0
    }
}
