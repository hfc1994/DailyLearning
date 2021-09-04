package Algorithm.Leetcode.within200;

import Algorithm.Leetcode.LeetcodeUtil;

/**
 * Created by user-hfc on 2021/9/1.
 *
 * 167. 两数之和 II - 输入有序数组
 *
 * 给定一个已按照 非递减顺序排列  的整数数组 numbers ，
 * 请你从数组中找出两个数满足相加之和等于目标数 target 。
 * 函数应该以长度为 2 的整数数组的形式返回这两个数的下标值。
 * numbers 的下标 从 1 开始计数 ，所以答案数组应当满足 1 <= answer[0] < answer[1] <= numbers.length 。
 * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
 *
 * 示例 1：
 * 输入：numbers = [2,7,11,15], target = 9
 * 输出：[1,2]
 * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。
 *
 * 示例 2：
 * 输入：numbers = [2,3,4], target = 6
 * 输出：[1,3]
 *
 * 示例 3：
 * 输入：numbers = [-1,0], target = -1
 * 输出：[1,2]
 *
 * 提示：
 * 2 <= numbers.length <= 3 * 10^4
 * -1000 <= numbers[i] <= 1000
 * numbers 按 非递减顺序 排列
 * -1000 <= target <= 1000
 * 仅存在一个有效答案
 *
 */
public class Problem167 {

    // 速度略慢，内存占用一般
    public int[] twoSum_1(int[] numbers, int target) {
        int diff;
        for (int i = 0; i < numbers.length - 1; i++) {
            diff = target - numbers[i];
            for (int j = i+1; j < numbers.length; j++) {
                if (numbers[j] == diff) {
                    return new int[] {i + 1, j + 1};
                } else if (numbers[j] > diff) {
                    break;
                }
            }
        }

        return null;
    }

    // 来自leetcode评论区
    public int[] twoSum_2(int[] numbers, int target) {
        int i = 0, j = numbers.length - 1;
        int temp;
        while (i < j) {
            temp = numbers[i] + numbers[j];
            if (temp == target) {
                return new int[] {i + 1, j + 1};
            } else if (temp > target) {
                j--;
            } else {
                i++;
            }
        }

        return null;
    }

    // 速度有所提升
    public int[] twoSum(int[] numbers, int target) {
        int diff, mid, end;
        for (int i = 0; i < numbers.length - 1; i++) {
            diff = target - numbers[i];
            end = numbers.length - 1;
            for (int j = i+1; j <= end;) {
                mid = (j + end) / 2;
                if (numbers[mid] == diff) {
                    return new int[] {i + 1, mid + 1};
                } else if (numbers[mid] > diff) {
                    end = mid - 1;
                    j++;
                    if (j > end) j = end;
                } else {
                    j = mid + 1;
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Problem167 p = new Problem167();

        LeetcodeUtil.printArray(p.twoSum(new int[] {2,7,11,15}, 9));    // [1,2]
        LeetcodeUtil.printArray(p.twoSum(new int[] {2,3,4}, 6));    // [1,3]
        LeetcodeUtil.printArray(p.twoSum(new int[] {-1,0}, -1));    // [1,2]
        LeetcodeUtil.printArray(p.twoSum(new int[] {3,24,50,79,88,150,345}, 200));    // [50,150]
    }

}
