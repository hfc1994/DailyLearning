package Algorithm.Leetcode;

import java.util.*;

/**
 * Created by user-hfc on 2020/9/4.
 *
 * 349. 两个数组的交集
 *
 * 给定两个数组，编写一个函数来计算它们的交集。
 *
 * 示例 1：
 * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
 * 输出：[2]
 *
 * 示例 2：
 * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
 * 输出：[9,4]
 *
 * 说明：
 * 输出结果中的每个元素一定是唯一的。
 * 我们可以不考虑输出结果的顺序。
 *
 */
public class Problem349 {

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> before = new HashSet<>((int) (0.75 * nums1.length));
        for (int i : nums1) before.add(i);

        Set<Integer> after = new HashSet<>(before.size());
        for (int i: nums2) {
            if (before.contains(i))
                after.add(i);
        }

        int[] ret = new int[after.size()];
        int count = 0;
        for (int i : after)
            ret[count++] = i;
        return ret;
    }

    public static void main(String[] args) {
        Problem349 p = new Problem349();

        int[] ret = p.intersection(new int[]{1,2,2,1}, new int[]{2,2});
        System.out.print("[");
        for (int i : ret)
            System.out.print(i + ",");
        System.out.print("]");
        System.out.println();

        ret = p.intersection(new int[]{4,9,5}, new int[]{9,4,9,8,4});
        System.out.print("[");
        for (int i : ret)
            System.out.print(i + ",");
        System.out.print("]");
        System.out.println();
    }
}
