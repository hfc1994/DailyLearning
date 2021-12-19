package Algorithm.Leetcode.pkg600;

import Algorithm.Leetcode.LeetcodeUtil;
import Algorithm.Leetcode.ListNode;

/**
 * Created by hfc on 2021/9/22.
 *
 * 725. 分隔链表
 *
 * 给你一个头结点为 head 的单链表和一个整数 k ，请你设计一个算法将链表分隔为 k 个连续的部分。
 * 每部分的长度应该尽可能的相等：任意两部分的长度差距不能超过 1 。这可能会导致有些部分为 null 。
 * 这 k 个部分应该按照在链表中出现的顺序排列，并且排在前面的部分的长度应该大于或等于排在后面的长度。
 * 返回一个由上述 k 部分组成的数组。
 *
 * 示例 1：
 * 1 -> 2 -> 3
 * 输入：head = [1,2,3], k = 5
 * 输出：[[1],[2],[3],[],[]]
 * 解释：
 * 第一个元素 output[0] 为 output[0].val = 1 ，output[0].next = null 。
 * 最后一个元素 output[4] 为 null ，但它作为 ListNode 的字符串表示是 [] 。
 *
 * 示例 2：
 * 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
 * 输入：head = [1,2,3,4,5,6,7,8,9,10], k = 3
 * 输出：[[1,2,3,4],[5,6,7],[8,9,10]]
 * 解释：
 * 输入被分成了几个连续的部分，并且每部分的长度相差不超过 1 。前面部分的长度大于等于后面部分的长度。
 *
 * 提示：
 * 链表中节点的数目在范围 [0, 1000]
 * 0 <= Node.val <= 1000
 * 1 <= k <= 50
 *
 */
public class Problem725 {

    // 速度：100.00%，内存：63.90%
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode[] retArray = new ListNode[k];
        if (head == null) return retArray;

        int size = 0;
        ListNode cur = head;
        while (cur != null) {
            size++;
            cur = cur.next;
        }

        int count = size / k;   // 每组平均多少个
        int left = size % k;    // 用去平均后剩余的
        int index = 0;
        while (index < retArray.length) {
            int tmpCount = 0;
            if (retArray[index] == null && head != null) {
                retArray[index] = head;
            }

            while (tmpCount < count) {
                cur = head;
                if (head != null) head = head.next;
                tmpCount++;
            }

            if (left > 0) {
                cur = head;
                if (head != null) head = head.next;
                left--;
            }

            if (cur != null)
                cur.next = null;

            index++;
        }

        return retArray;
    }

    public static void main(String[] args) {
        Problem725 p = new Problem725();

        ListNode listNode = ListNode.buildList(new int[] {1, 2, 3});
        printArray(p.splitListToParts(listNode, 5));

        listNode = ListNode.buildList(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        printArray(p.splitListToParts(listNode, 3));
    }

    private static void printArray(ListNode[] array) {
        System.out.println("[");
        for (ListNode l : array) {
            System.out.print("   ");
            LeetcodeUtil.printListNode(l);
        }
        System.out.println("]");
    }

}
