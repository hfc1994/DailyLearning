package Algorithm.Leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hfc on 2020/9/9.
 *
 * 39. 组合总和
 *
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 *
 * 示例 1：
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [
 *   [7],
 *   [2,2,3]
 * ]
 *
 * 示例 2：
 * 输入：candidates = [2,3,5], target = 8,
 * 所求解集为：
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *  
 *
 * 提示：
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都是独一无二的。
 * 1 <= target <= 500
 *
 */
public class Problem39 {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        return addToTarget(candidates, target, 0);
    }

    private List<List<Integer>> addToTarget(int[] candidates, int target, int index) {
        List<List<Integer>> parts = new LinkedList<>();
        List<List<Integer>> retList;
        List<Integer> ret;
        int candidate;
        for (int i=index; i<candidates.length; i++) {
            candidate = candidates[i];
            if (candidate == target) {
                ret = new ArrayList<>(1);
                ret.add(candidate);
                parts.add(ret);
            } else if (candidate < target) {
                // 搜索子数字组合，使用i做为索引的原因在于：
                //      使用主数字2得到2+3+5=10，那么使用主数字3时，如果不提前排除掉2，
                //      那么肯定会出现3+2+5=10的结果。而3与2的所有组合在以2为主数字时肯定已经全部集齐。
                retList = addToTarget(candidates, target - candidate, i);
                if (retList.size() > 0) {
                    int finalCandidate = candidate;
                    retList.forEach(rets -> {
                        rets.add(finalCandidate);
                        parts.add(rets);
                    });
                }
            }
        }
        return parts;
    }

    public static void main(String[] args) {
        Problem39 p = new Problem39();

        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        System.out.println("[");
        p.combinationSum(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");

        System.out.println("====================");

        candidates = new int[]{2, 3, 5};
        target = 8;
        System.out.println("[");
        p.combinationSum(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");

        System.out.println("====================");

        candidates = new int[]{2, 3, 5};
        target = 1;
        System.out.println("[");
        p.combinationSum(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");

        System.out.println("====================");

        candidates = new int[]{};
        target = 1;
        System.out.println("[");
        p.combinationSum(candidates, target).forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("]");
        });
        System.out.println("]");
    }
}
