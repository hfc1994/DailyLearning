package Algorithm.Leetcode.pkg200;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2020/9/11.
 *
 * 216. 组合总和 III
 *
 * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 *
 * 说明：
 * 所有数字都是正整数。
 * 解集不能包含重复的组合。 
 *
 * 示例 1:
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 *
 * 示例 2:
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 *
 */
public class Problem216 {

    private List<List<Integer>> parts;
    private int maxDepth;

    public List<List<Integer>> combinationSum3(int k, int n) {
        LinkedList<Integer> ret = new LinkedList<>();
        int[] candidates = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        parts = new LinkedList<>();
        maxDepth = k;

        addToTarget(candidates, n, 0, 1, ret);
        return parts;
    }

    private void addToTarget(int[] candidates, int target, int index, int depth, LinkedList<Integer> ret) {
        int cdd;
        for (int i=index; i<candidates.length; i++) {
            cdd = candidates[i];
            if (cdd == target && depth == maxDepth) {
                ret.addLast(cdd);
                parts.add(new LinkedList<>(ret));
                ret.removeLast();
            } else if (cdd > target) {
                // 剪枝，已排过序，后面的肯定全部不符合
                break;
            } else if (depth < maxDepth) {
                ret.addLast(cdd);
                addToTarget(candidates, target - cdd, i+1, depth+1, ret);
                ret.removeLast();
            }
        }
    }

    public static void main(String[] args) {
        Problem216 p = new Problem216();

        int k = 3;
        int n = 7;
        System.out.print("[");
        p.combinationSum3(k, n).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.print("],");
        });
        System.out.println("]");

        System.out.println("--------------");

        k = 3;
        n = 9;
        System.out.print("[");
        p.combinationSum3(k, n).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.print("],");
        });
        System.out.println("]");

        System.out.println("--------------");

        k = 1;
        n = 1;
        System.out.print("[");
        p.combinationSum3(k, n).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.print("],");
        });
        System.out.println("]");

        System.out.println("--------------");

        k = 1;
        n = 100;
        System.out.print("[");
        p.combinationSum3(k, n).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.print("],");
        });
        System.out.println("]");
    }

}
