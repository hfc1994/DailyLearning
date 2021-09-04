package Algorithm.Leetcode.within200;

import Algorithm.Leetcode.LeetcodeUtil;
import Algorithm.Leetcode.ListNode;

/**
 * Created by user-hfc on 2021/9/4.
 *
 * 19. 删除链表的倒数第 N 个结点
 *
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 * 进阶：你能尝试使用一趟扫描实现吗？
 *
 * 示例 1：
 * 输入：head = [1,2,3,4,5], n = 2
 * 输出：[1,2,3,5]
 *
 * 示例 2：
 * 输入：head = [1], n = 1
 * 输出：[]
 *
 * 示例 3：
 * 输入：head = [1,2], n = 1
 * 输出：[1]
 *
 * 提示：
 * 链表中结点的数目为 sz
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 *
 */
public class Problem19 {

    // 逻辑过于复杂，速度优秀
    public ListNode removeNthFromEnd_1(ListNode head, int n) {
        ListNode nextNode = head.next, tempNode;

        head.next = null;
        while (nextNode != null) {
            tempNode = nextNode.next;
            nextNode.next = head;
            head = nextNode;
            nextNode = tempNode;
        }

        int idx = 1;
        nextNode = head.next;
        head.next = null;
        while (true) {
            if (idx == n) {
                tempNode = head;
                head = head.next;
                tempNode.next = null;
                if (head == nextNode && nextNode != null) {
                    nextNode = nextNode.next;
                }
            }

            if (nextNode == null) break;

            tempNode = nextNode.next;
            nextNode.next = head;
            head = nextNode;
            nextNode = tempNode;
            idx++;
        }

        return head;
    }

    // 速度优秀
    public ListNode removeNthFromEnd_2(ListNode head, int n) {
        ListNode curNode = head, prevNode = null;
        int count = 0;
        while (curNode != null) {
            count++;
            curNode = curNode.next;
        }

        count = count - n;
        curNode = head;
        while (count-- != 0) {
            prevNode = curNode;
            curNode = curNode.next;
        }

        // curNode就是需要删除的
        if (prevNode == null) {
            // 第一个
            head = curNode.next;
        } else {
            prevNode.next = curNode.next;
        }

        return head;
    }

    // 来自leetcode评论区
    private int count;
    public ListNode removeNthFromEnd_3(ListNode head, int n) {
        count = 0;
        return dfs(head, n);
    }

    private ListNode dfs(ListNode curNode, int n) {
        if (curNode.next != null) {
            curNode.next = dfs(curNode.next, n);
        }
        count++;

        if (count == n) {
            return curNode.next;
        }
        return curNode;
    }

    // 来自leetcode评论区，快慢指针
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head, slow = head;

        for (int i = 1; i <= n ; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            return head.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return head;
    }

    public static void main(String[] args) {
        Problem19 p = new Problem19();

        LeetcodeUtil.printListNode(p.removeNthFromEnd(
                LeetcodeUtil.buildListNode(new int[] {1,2,3,4,5}), 2)); // [1,2,3,5]

        LeetcodeUtil.printListNode(p.removeNthFromEnd(
                LeetcodeUtil.buildListNode(new int[] {1,2,3,4,5}), 1)); // [1,2,3,4]

        LeetcodeUtil.printListNode(p.removeNthFromEnd(
                LeetcodeUtil.buildListNode(new int[] {1,2,3,4,5}), 5)); // [2,3,4,5]

        LeetcodeUtil.printListNode(p.removeNthFromEnd(
                LeetcodeUtil.buildListNode(new int[] {1}), 1)); // []

        LeetcodeUtil.printListNode(p.removeNthFromEnd(
                LeetcodeUtil.buildListNode(new int[] {1,2}), 1));   // [1]

        LeetcodeUtil.printListNode(p.removeNthFromEnd(
                LeetcodeUtil.buildListNode(new int[] {1,2}), 2));   // [2]
    }

}
