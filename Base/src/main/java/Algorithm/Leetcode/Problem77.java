package Algorithm.Leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by user-hfc on 2020/9/8.
 *
 * 77. 组合
 *
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 *
 * 示例:
 * 输入: n = 4, k = 2
 *
 * 输出:
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 */
public class Problem77 {

    public List<List<Integer>> combine(int n, int k) {
        List<Integer> numList = new ArrayList<>();
        for (int i=1; i<=n; i++)
            numList.add(i);
        return buildLengthOfExcept(k, 0, numList);
    }

    /**
     * 基本思路是，先从数字中拿走一个数字A，然后剩余数字组合拼接一个总长度-1的数字列表B，
     * 最后数字A与数字列表B组合成最终的结果
     *
     * @param len 所需数字组合的长度
     * @param index 在候选数字数组中的指针，用于实现拿走数字A的逻辑
     * @param numList 候选数字数组
     */
    private LinkedList<List<Integer>> buildLengthOfExcept(int len, int index, List<Integer> numList) {

        // head是当前层级的首数字
        // newLen是剩余需要的数字长度
        int head, newLen, curIndex;
        int length = numList.size();

        LinkedList<List<Integer>> retList = new LinkedList<>();

        if (len == 0)
            return retList;

        List<Integer> ret;
        for (int i=index; i<=length-len; i++) {
            head = numList.get(i);
            curIndex = i+1;
            newLen = len - 1;

            if (newLen == 0) {
                ret = new ArrayList<>(1);
                ret.add(head);
                retList.add(ret);
            } else if (newLen == 1) {
                for (int j=curIndex; j<length; j++) {
                    List<Integer> tmpRet = new ArrayList<>(2);
                    tmpRet.add(head);
                    tmpRet.add(numList.get(j));
                    retList.add(tmpRet);
                }
            } else {
                LinkedList<List<Integer>> subRetList = buildLengthOfExcept(newLen, curIndex, numList);
                int finalHead1 = head;
                subRetList.forEach(subRet -> {
                    subRet.add(0, finalHead1);
                    retList.add(subRet);
                });
            }
        }

        return retList;
    }

    public static void main(String[] args) {
        Problem77 p = new Problem77();

        List<List<Integer>> retList = p.combine(4, 2);
        System.out.println("[");
        retList.forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("],");
        });
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(5, 3);
        System.out.println("[");
        retList.forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("],");
        });
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(5, 0);
        System.out.println("[");
        retList.forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("],");
        });
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(1, 1);
        System.out.println("[");
        retList.forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("],");
        });
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(10, 4);
        System.out.println("[");
        retList.forEach(ret -> {
            System.out.print("[");
            ret.forEach(r -> System.out.print(r + ","));
            System.out.println("],");
        });
        System.out.println("]");
    }

}
