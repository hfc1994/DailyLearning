package Algorithm.Leetcode.pkg0;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.*;

/**
 * Created by user-hfc on 2020/9/8.
 *
 * 77. 组合
 *
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 * 你可以按 任何顺序 返回答案。
 *
 * 示例 1：
 * 输入：n = 4, k = 2
 * 输出：
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 *
 * 示例 2：
 * 输入：n = 1, k = 1
 * 输出：[[1]]
 *
 * 提示：
 * 1 <= n <= 20
 * 1 <= k <= n
 *
 */
public class Problem77 {

    public List<List<Integer>> combine_2(int n, int k) {
        if (k == 0)
            return Collections.emptyList();

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
    private LinkedList<List<Integer>> buildLengthOfExcept_1(int len, int index, List<Integer> numList) {

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
                LinkedList<List<Integer>> subRetList = buildLengthOfExcept_1(newLen, curIndex, numList);
                int finalHead1 = head;
                subRetList.forEach(subRet -> {
                    subRet.add(0, finalHead1);
                    retList.add(subRet);
                });
            }
        }

        return retList;
    }

    // buildLengthOfExcept_1基础上的修改版，速度快不少，内存占用更低
    private List<List<Integer>> buildLengthOfExcept(int len, int index, List<Integer> numList) {

        List<List<Integer>> retList = new LinkedList<>();

        List<Integer> ret;
        List<List<Integer>> tmpRetList;
        for (int i = index; i <= numList.size() - len; i++) {
            // 也就是 len - 1 == 0，剩余需要的数字长度
            if (len == 1) {
                ret = new ArrayList<>(len + index);
                ret.add(numList.get(i));    // 添加当前层级的首数字
                retList.add(ret);
            } else {
                tmpRetList = buildLengthOfExcept(len - 1, i + 1, numList);
                for (List<Integer> aRetList : tmpRetList) {
                    aRetList.add(0, numList.get(i));    // 添加当前层级的首数字
                }
                retList.addAll(tmpRetList);
            }
        }

        return retList;
    }

    public List<List<Integer>> combine(int n, int k) {
        if (k == 0)
            return Collections.emptyList();

        return buildLengthOfExcept_2(k, 1, n);
    }

    // 替换链表的实现，去除需要插入链表头部的逻辑
    // 速度和内存均有提升，接近最优速度和内存占用
    private List<List<Integer>> buildLengthOfExcept_2(int len, int begin, int limit) {

        List<List<Integer>> retList = new ArrayList<>();

        List<Integer> ret;
        List<List<Integer>> tmpRetList;
        for (int i = begin; i <= limit - len + 1; i++) {
            // 也就是 len - 1 == 0，剩余需要的数字长度
            if (len == 1) {
                ret = new ArrayList<>(len + begin);
                ret.add(limit - i + 1);    // 添加当前层级的首数字
                retList.add(ret);
            } else {
                tmpRetList = buildLengthOfExcept_2(len - 1, i + 1, limit);
                for (List<Integer> aRetList : tmpRetList) {
                    aRetList.add(limit - i + 1);    // 添加当前层级的首数字
                    retList.add(aRetList);
                }
            }
        }

        return retList;
    }

    public static void main(String[] args) {
        Problem77 p = new Problem77();

        List<List<Integer>> retList = p.combine(4, 2);
        System.out.println("[");
        retList.forEach(LeetcodeUtil::printList);
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(5, 3);
        System.out.println("[");
        retList.forEach(LeetcodeUtil::printList);
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(5, 0);
        System.out.println("[");
        retList.forEach(LeetcodeUtil::printList);
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(1, 1);
        System.out.println("[");
        retList.forEach(LeetcodeUtil::printList);
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(10, 4);
        System.out.println("[");
        retList.forEach(LeetcodeUtil::printList);
        System.out.println("]");

        System.out.println("===============");

        retList = p.combine(3, 3);
        System.out.println("[");
        retList.forEach(LeetcodeUtil::printList);
        System.out.println("]");
    }

}
