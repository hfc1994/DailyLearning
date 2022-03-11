package Algorithm.Leetcode.pkg400;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hfc on 2022/3/6.
 *
 * 496. 下一个更大元素 I
 * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
 * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
 * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定
 *  nums2[j] 的 下一个更大元素 。如果不存在下一个更大元素，那么本次查询的答案是 -1 。
 * 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
 *
 * 示例 1：
 * 输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
 * 输出：[-1,3,-1]
 * 解释：nums1 中每个值的下一个更大元素如下所述：
 * - 4 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
 * - 1 ，用加粗斜体标识，nums2 = [1,3,4,2]。下一个更大元素是 3 。
 * - 2 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
 *
 * 示例 2：
 * 输入：nums1 = [2,4], nums2 = [1,2,3,4].
 * 输出：[3,-1]
 * 解释：nums1 中每个值的下一个更大元素如下所述：
 * - 2 ，用加粗斜体标识，nums2 = [1,2,3,4]。下一个更大元素是 3 。
 * - 4 ，用加粗斜体标识，nums2 = [1,2,3,4]。不存在下一个更大元素，所以答案是 -1 。
 *  
 * 提示：
 * 1 <= nums1.length <= nums2.length <= 1000
 * 0 <= nums1[i], nums2[i] <= 104
 * nums1和nums2中所有整数 互不相同
 * nums1 中的所有整数同样出现在 nums2 中
 *
 * 进阶：你可以设计一个时间复杂度为 O(nums1.length + nums2.length) 的解决方案吗？
 *
 */
public class Problem496 {

    /**
     * 速度 91%
     * 内存 17%
     *
     * 单调栈
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> meta = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums2.length; i++) {
            int num = nums2[i];
            meta.put(num, -1);
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                meta.put(stack.peek(), nums2[i]);
                stack.pop();
            }
            stack.push(num);
        }

        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = meta.get(nums1[i]);
        }
        return nums1;
    }

    public static void main(String[] args) {
        Problem496 p = new Problem496();

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {-1, 3, -1},
                p.nextGreaterElement(new int[] {4, 1, 2}, new int[]{1, 3, 4, 2})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {3, -1},
                p.nextGreaterElement(new int[] {2, 4}, new int[]{1, 2, 3, 4})));

        System.out.println(LeetcodeUtil.equalsOfArray(new int[] {-1, 6, 5, 7, 5},
                p.nextGreaterElement(new int[] {7, 3, 1, 5, 4}, new int[]{8, 4, 1, 5, 2, 7, 3, 6})));
    }
}
