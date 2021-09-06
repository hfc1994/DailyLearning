package Algorithm.Leetcode.pkg0;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2020/9/10.
 *
 * 40. 组合总和 II
 *
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用一次。
 *
 * 说明：
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。 
 *
 * 示例 1:
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 *
 * 示例 2:
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 *
 */
public class Problem40 {

    private List<List<Integer>> parts;
    private int length;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        LinkedList<Integer> ret = new LinkedList<>();
        parts = new LinkedList<>();
        length = candidates.length;

        // 对数组排序，方便后续处理
        doSort(candidates, 0, candidates.length - 1);
        addToTarget(candidates, target, 0, ret);
        return parts;
    }

    // 快速排序
    private void doSort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int k = partition(a, lo, hi);
        doSort(a, lo, k-1);
        doSort(a, k+1, hi);
    }

    private int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi+1;
        int v = a[lo];
        int tmp;
        while (true) {
            while (a[++i] < v) if (i == hi) break;
            while (a[--j] > v) if (j == lo) break;

            if (i >= j) break;
            tmp = a[j];
            a[j] = a[i];
            a[i] = tmp;
        }

        tmp = a[lo];
        a[lo] = a[j];   // j指向更小的数
        a[j] = tmp;

        return j;
    }

    private void addToTarget(int[] candidates, int target, int index, LinkedList<Integer> ret) {
        int cdd;
        for (int i=index; i<length;) {
            cdd = candidates[i];
            if (cdd == target) {
                ret.addLast(cdd);
                parts.add(new LinkedList<>(ret));
                ret.removeLast();
            } else if (cdd > target) {
                // 剪枝，已排过序，后面的肯定全部不符合
                break;
            } else {
                ret.addLast(cdd);
                addToTarget(candidates, target - cdd, i+1, ret);
                ret.removeLast();
            }

            i++;
            // 重复的元素就不要用了，比如连续两个3，第二个3能做的事儿，第一个3都做过了
            while (i < length && cdd == candidates[i]) {
                i++;
            }
        }
    }

    public static void main(String[] args) {
        Problem40 p = new Problem40();

//        int[] a = {9, 50, 5, 14, 23, 76, 0, 33, 41, 43, 94, 51, 78, 50, 70, 61, 55, 54, 78, 60};
//        p.doSort(a, 0, a.length-1);
//        for (int i: a)
//            System.out.print(i + " ");
//        System.out.println();

        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        System.out.println("[");
        p.combinationSum2(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");

        System.out.println("====================");

        candidates = new int[]{2, 5, 2, 1, 2};
        target = 5;
        System.out.println("[");
        p.combinationSum2(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");

        System.out.println("====================");

        candidates = new int[]{};
        target = 0;
        System.out.println("[");
        p.combinationSum2(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");

        System.out.println("====================");

        candidates = new int[]{1};
        target = 1;
        System.out.println("[");
        p.combinationSum2(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");

    }
}
