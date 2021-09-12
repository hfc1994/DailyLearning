package Algorithm.Leetcode.pkg0;

import Algorithm.Leetcode.LeetcodeUtil;
import Algorithm.Leetcode.ListNode;

/**
 * Created by user-hfc on 2021/9/10.
 *
 * 21. 合并两个有序链表
 *
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接
 * 给定的两个链表的所有节点组成的。
 *
 * 示例 1：
 *
 *   1  ->  2  ->  4
 *   1  ->  3  ->  4
 * 合并后： 1  ->  1  ->  2  ->  3  ->  4  ->  4
 *
 * 输入：l1 = [1,2,4], l2 = [1,3,4]
 * 输出：[1,1,2,3,4,4]
 *
 * 示例 2：
 * 输入：l1 = [], l2 = []
 * 输出：[]
 *
 * 示例 3：
 * 输入：l1 = [], l2 = [0]
 * 输出：[0]
 *
 * 提示：
 * 两个链表的节点数目范围是 [0, 50]
 * -100 <= Node.val <= 100
 * l1 和 l2 均按 非递减顺序 排列
 *
 */
public class Problem21 {

    // 来自leetcode评论区，内存占用更低
    public ListNode mergeTwoLists_1(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head = l1.val <= l2.val ? l1 : l2;
        head.next = mergeTwoLists_1(head.next, l1.val <= l2.val ? l2 : l1);
        return head;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode cur;
        if (l1.val <= l2.val) {
            cur = l1;
            l1 = l1.next;
        } else {
            cur = l2;
            l2 = l2.next;
        }
        ListNode head = cur;

        while (l1 != null || l2 != null) {
            if (l1 == null) {
                cur.next = l2;
                l2 = l2.next;
            } else if (l2 == null) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                if (l1.val <= l2.val) {
                    cur.next = l1;
                    l1 = l1.next;
                } else {
                    cur.next = l2;
                    l2 = l2.next;
                }
            }
            cur = cur.next;
        }

        return head;
    }

    public static void main(String[] args) {
        Problem21 p = new Problem21();

        ListNode l1 = ListNode.buildList(new int[] { 1,2,4 });
        ListNode l2 = ListNode.buildList(new int[] { 1,3,4 });
        LeetcodeUtil.printListNode(p.mergeTwoLists(l1, l2));    // [1,1,2,3,4,4]

        l1 = ListNode.buildList(new int[] {});
        l2 = ListNode.buildList(new int[] {});
        LeetcodeUtil.printListNode(p.mergeTwoLists(l1, l2));    // []

        l1 = ListNode.buildList(new int[] {});
        l2 = ListNode.buildList(new int[] { 0 });
        LeetcodeUtil.printListNode(p.mergeTwoLists(l1, l2));    // [0]
    }

}
