package Algorithm.Leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hfc on 2020/9/5.
 *
 * 60. 第k个排列
 *
 * 给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
 * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
 *
 * "123"
 * "132"
 * "213"
 * "231"
 * "312"
 * "321"
 * 给定 n 和 k，返回第 k 个排列。
 *
 * 说明：
 * 给定 n 的范围是 [1, 9]。
 * 给定 k 的范围是[1,  n!]。
 *
 * 示例 1:
 * 输入: n = 3, k = 3
 * 输出: "213"
 *
 * 示例 2:
 * 输入: n = 4, k = 9
 * 输出: "2314"
 *
 */
public class Problem60 {

    public String getPermutation(int n, int k) {

        List<Integer> list = new LinkedList<>();
        // n个数的排列组合总数，按题意总量应该不会使int溢出
        int total = 1;
        for (int i = 1; i <= n; i++) {
            list.add(i);
            total *= i;
        }

        StringBuilder permutation = new StringBuilder();
        int times;
        while (n > 0) {
            // 每种最高位对应的子数字组合的总数为这个新total
            total = total / n;
            // times表示的是哪个最高位，偏移值，从0开始
            times = (k-1) / total;
            permutation.append(list.remove(times));
            // 子数字的所有排列组合的第remain个是目标值
            k = k - times * total;

            // 最高位确认后，处理剩余数字的组合
            n--;
        }

        return permutation.toString();
    }

    public static void main(String[] args) {
        Problem60 p = new Problem60();
        System.out.println(p.getPermutation(3, 3)); // 213
        System.out.println(p.getPermutation(4, 9)); // 2314

        System.out.println(p.getPermutation(0, 0)); //

        System.out.println(p.getPermutation(4, 24)); // 4321
        System.out.println(p.getPermutation(2, 2)); // 21
    }
}
