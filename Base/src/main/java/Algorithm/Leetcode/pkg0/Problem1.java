package Algorithm.Leetcode.pkg0;

/**
 * Created by hfc on 2020/8/28.
 *
 * 1. 两数之和
 *
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
 *
 * 示例:
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 */
public class Problem1 {

    public int[] twoSum2(int[] nums, int target) {
        int[] ret = new int[2];
        for (int i=1; i<=nums.length-1; i++) {
            for (int j=0; j<nums.length-i; j++) {
                if (nums[j] + nums[i+j] == target) {
                    ret[0] = j;
                    ret[1] = i+j;

                    i=j=nums.length;
                }
            }

        }
        return ret;
    }

    public static void main(String[] args) {
        Problem1 p = new Problem1();

        int[] nums = new int[] { 2, 7, 11, 15 };
        System.out.print("[");
        for (int i : p.twoSum2(nums, 9)) {
            System.out.print(i + ",");
        }
        System.out.println("]");
    }
}
