package Algorithm.Leetcode.within400;

import Algorithm.Leetcode.LeetcodeUtil;

/**
 * Created by user-hfc on 2021/9/1.
 *
 * 283. 移动零
 *
 * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
 *
 * 示例:
 * 输入: [0,1,0,3,12]
 * 输出: [1,3,12,0,0]
 *
 * 说明:
 * 必须在原数组上操作，不能拷贝额外的数组。
 * 尽量减少操作次数。
 *
 */
public class Problem283 {

    // 来自leetcode评论区
    public void moveZeroes_1(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j++] = nums[i];
            }
        }

        while (j < nums.length) {
            nums[j++] = 0;
        }
    }

    public void moveZeroes(int[] nums) {
        int zeroIdx = 0, nonZeroIdx = 0;

        int temp;
        while (true) {
            // 从左往右找第一个0
            while (nums[zeroIdx] != 0) {
                zeroIdx++;
                if (zeroIdx == nums.length) return;
            }

            if (zeroIdx > nonZeroIdx) {
                nonZeroIdx = zeroIdx;
            }

            // 从左往右找第一个非0
            while (nums[nonZeroIdx] == 0) {
                nonZeroIdx++;
                if (nonZeroIdx == nums.length) return;
            }

            if (zeroIdx >= nonZeroIdx) break;

            temp = nums[zeroIdx];
            nums[zeroIdx] = nums[nonZeroIdx];
            nums[nonZeroIdx] = temp;
        }
    }

    public static void main(String[] args) {
        Problem283 p = new Problem283();

        int[] nums = new int[] {0,1,0,3,12};
        p.moveZeroes(nums);
        LeetcodeUtil.printArray(nums);  // [1,3,12,0,0]

        nums = new int[] {0,1,0,1,0,4,5,0,5,2,0,3,8,9,3,12,0};
        p.moveZeroes(nums);
        LeetcodeUtil.printArray(nums);  // [1,1,4,5,5,2,3,8,9,3,12,0,0,0,0,0,0]

        nums = new int[] {0,0};
        p.moveZeroes(nums);
        LeetcodeUtil.printArray(nums);  // [0,0]

        nums = new int[] {1,1};
        p.moveZeroes(nums);
        LeetcodeUtil.printArray(nums);  // [1,1]

        nums = new int[] {1,0};
        p.moveZeroes(nums);
        LeetcodeUtil.printArray(nums);  // [1,0]

        nums = new int[] {0,1};
        p.moveZeroes(nums);
        LeetcodeUtil.printArray(nums);  // [1,0]

        nums = new int[] {1,0,1};
        p.moveZeroes(nums);
        LeetcodeUtil.printArray(nums);  // [1,1,0]
    }

}
