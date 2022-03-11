package Algorithm.Leetcode.pkg800;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by hfc on 2022/3/2.
 *
 * 969. 煎饼排序
 *
 * 给你一个整数数组 arr ，请使用 煎饼翻转 完成对数组的排序。
 * 一次煎饼翻转的执行过程如下：
 * - 选择一个整数 k ，1 <= k <= arr.length
 * - 反转子数组 arr[0...k-1]（下标从 0 开始）
 * 例如，arr = [3,2,1,4] ，选择 k = 3 进行一次煎饼翻转，反转子数组 [3,2,1] ，
 * 得到 arr = [1,2,3,4] 。
 * 以数组形式返回能使 arr 有序的煎饼翻转操作所对应的 k 值序列。任何将数组排序
 * 且翻转次数在 10 * arr.length 范围内的有效答案都将被判断为正确。
 *
 * 示例 1：
 * 输入：[3,2,4,1]
 * 输出：[4,2,4,3]
 * 解释：
 * 我们执行 4 次煎饼翻转，k 值分别为 4，2，4，和 3。
 * 初始状态 arr = [3, 2, 4, 1]
 * 第一次翻转后（k = 4）：arr = [1, 4, 2, 3]
 * 第二次翻转后（k = 2）：arr = [4, 1, 2, 3]
 * 第三次翻转后（k = 4）：arr = [3, 2, 1, 4]
 * 第四次翻转后（k = 3）：arr = [1, 2, 3, 4]，此时已完成排序。
 *
 * 示例 2：
 * 输入：[1,2,3]
 * 输出：[]
 * 解释：
 * 输入已经排序，因此不需要翻转任何内容。
 * 请注意，其他可能的答案，如 [3，3] ，也将被判断为正确。
 *
 * 提示：
 * 1 <= arr.length <= 100
 * 1 <= arr[i] <= arr.length
 * arr 中的所有整数互不相同（即，arr 是从 1 到 arr.length 整数的一个排列）
 *
 */
public class Problem969 {

    /**
     * 速度 100%
     * 内存 27%
     */
    public List<Integer> pancakeSort(int[] arr) {
        List<Integer> flips = new ArrayList<>();
        int idx = arr.length - 1;
        while (idx > 0) {
            int max = Integer.MIN_VALUE;
            int maxIdx = -1;
            for (int i = 0; i <= idx; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                    maxIdx = i;
                }
            }

            if (maxIdx == idx) {
                idx--;
                continue;
            }

            if (0 != maxIdx) {
                this.doPancake(arr, 0, maxIdx);
                flips.add(maxIdx + 1);
            }
            this.doPancake(arr, 0, idx);
            flips.add(idx + 1);
            idx--;
        }

        return flips;
    }

    private void doPancake(int[] arr, int begin, int end) {
        if (begin == end) return;
        while (begin < end) {
            int tmp = arr[begin];
            arr[begin] = arr[end];
            arr[end] = tmp;
            begin++;
            end--;
        }
    }

    public static void main(String[] args) {
        Problem969 p = new Problem969();

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(3, 4, 2, 3, 2),
                p.pancakeSort(new int[] {3, 2, 4, 1})));

        System.out.println(LeetcodeUtil.equalsOfList(Collections.emptyList(),
                p.pancakeSort(new int[] {1, 2, 3})));

        System.out.println(LeetcodeUtil.equalsOfList(Collections.emptyList(),
                p.pancakeSort(new int[] {1})));

        System.out.println(p.pancakeSort(new int[] {7, 3, 2, 1, 4, 6, 5}));
    }
}
