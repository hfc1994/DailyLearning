package Algorithm.Leetcode.pkg200;

import Algorithm.Leetcode.LeetcodeUtil;
import Algorithm.Leetcode.ListNode;

/**
 * Created by user-hfc on 2021/9/10.
 *
 * 206. 反转链表
 *
 * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
 *
 * 示例 1：
 *         1  ->  2  ->  3  ->  4  ->  5
 * 转换后： 5  ->  4  ->  3  ->  2  ->  1
 *
 * 输入：head = [1,2,3,4,5]
 * 输出：[5,4,3,2,1]
 *
 * 示例 2：
 *         1  ->  2
 * 转换后： 2  ->  1
 *
 * 输入：head = [1,2]
 * 输出：[2,1]
 *
 * 示例 3：
 * 输入：head = []
 * 输出：[]
 *
 * 提示：
 * 链表中节点的数目范围是 [0, 5000]
 * -5000 <= Node.val <= 5000
 *
 */
public class Problem206 {

    // 来自leetcode评论区，速度相似，内存占用更差一些
    public ListNode reverseList_2(ListNode head) {
        return doReverse(null, head);
    }

    private ListNode doReverse(ListNode prev, ListNode cur) {
        if (cur == null) return prev;
        ListNode next = cur.next;
        cur.next = prev;
        return doReverse(cur, next);
    }

    public ListNode reverseList_1(ListNode head) {
        if (head == null) return null;

        ListNode cur = head.next;
        head.next = null;
        ListNode tmp;
        while (cur != null) {
            tmp = cur;
            cur = cur.next;
            tmp.next = head;
            head = tmp;
        }

        return head;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null) return null;

        ListNode prev = null, next;
        ListNode cur = head;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        return prev;
    }

    public static void main(String[] args) {
        Problem206 p = new Problem206();

        ListNode l = ListNode.buildList(new int[] { 1,2,3,4,5 });
        LeetcodeUtil.printListNode(p.reverseList(l));   // [5,4,3,2,1]

        l = ListNode.buildList(new int[] { 1,2 });
        LeetcodeUtil.printListNode(p.reverseList(l));   // [2,1]

        l = ListNode.buildList(new int[] {});
        LeetcodeUtil.printListNode(p.reverseList(l));   // []
    }
}
