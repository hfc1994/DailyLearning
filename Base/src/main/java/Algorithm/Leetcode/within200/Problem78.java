package Algorithm.Leetcode.within200;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hfc on 2020/9/20.
 *
 * 78. 子集
 *
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 *
 * 说明：解集不能包含重复的子集。
 *
 * 示例:
 * 输入: nums = [1,2,3]
 * 输出:
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 *
 */
public class Problem78 {

    private List<List<Integer>> rets;

    public List<List<Integer>> subsets(int[] nums) {
//        return solution1(nums);
        return solution2(nums);
    }

    public List<List<Integer>> solution1(int[] nums) {
        rets = new LinkedList<>();
        List<Integer> ret = new LinkedList<>();
        for (int i=0; i<=nums.length; i++) {
            doDfs(nums, 0, i, ret);
        }
        return rets;
    }

    // 官方解法，使用数据位来代表是否使用某个数字
    // 0~1<<length-1之间的所有数的二进制位组合就是所有可能的不重复的子集
    // 第i位的二进制数为1，表示nums[i]在子集中；反之就是不存在子集中
    public List<List<Integer>> solution2(int[] nums) {
        rets = new LinkedList<>();
        List<Integer> ret = new LinkedList<>();
        int length = nums.length;

        for (int mask=0; mask<(1 << length); mask++) {
            ret.clear();
            for (int i=0; i<length; i++) {
                if ((mask & (1 << i)) != 0)
                    ret.add(nums[i]);
            }

            rets.add(new LinkedList<>(ret));
        }

        return rets;
    }

    private void doDfs(int[] num, int idx, int length, List<Integer> ret) {
        if (length == 0)
            rets.add(new LinkedList<>(ret));

        for (int i=idx; i<num.length; i++) {
            ret.add(num[i]);
            doDfs(num, i+1, length-1, ret);
            ret.remove(ret.size()-1);
        }
    }

    public static void main(String[] args) {
        Problem78 p = new Problem78();

        int[] nums = {1, 2, 3};
        System.out.println("[");
        for (List<Integer> ret : p.subsets(nums)) {
            System.out.print("[");
            ret.forEach(val -> System.out.print(val + ", "));
            System.out.println("],");
        }
        System.out.println("]");

        System.out.println("------------");

        nums = new int[]{1};
        System.out.println("[");
        for (List<Integer> ret : p.subsets(nums)) {
            System.out.print("[");
            ret.forEach(val -> System.out.print(val + ", "));
            System.out.println("],");
        }
        System.out.println("]");

        System.out.println("------------");

        nums = new int[]{};
        System.out.println("[");
        for (List<Integer> ret : p.subsets(nums)) {
            System.out.print("[");
            ret.forEach(val -> System.out.print(val + ", "));
            System.out.println("],");
        }
        System.out.println("]");
    }
}
