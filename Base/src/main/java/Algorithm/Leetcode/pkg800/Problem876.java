package Algorithm.Leetcode.pkg800;

import Algorithm.Leetcode.LeetcodeUtil;
import Algorithm.Leetcode.ListNode;

/**
 * Created by user-hfc on 2021/9/4.
 *
 * 876. 链表的中间结点
 *
 * 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
 * 如果有两个中间结点，则返回第二个中间结点。
 *
 * 示例 1：
 * 输入：[1,2,3,4,5]
 * 输出：此列表中的结点 3 (序列化形式：[3,4,5])
 * 返回的结点值为 3 。 (测评系统对该结点序列化表述是 [3,4,5])。
 * 注意，我们返回了一个 ListNode 类型的对象 ans，这样：
 * ans.val = 3, ans.next.val = 4, ans.next.next.val = 5, 以及 ans.next.next.next = NULL.
 *
 * 示例 2：
 * 输入：[1,2,3,4,5,6]
 * 输出：此列表中的结点 4 (序列化形式：[4,5,6])
 * 由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。
 *
 * 提示：
 * 给定链表的结点数介于 1 和 100 之间。
 *
 */
public class Problem876 {

    // 来自leetcode评论区，快慢指针
    public ListNode middleNode_1(ListNode head) {
        ListNode p = head, q = head;
        while (q != null && q.next != null) {
            q = q.next.next;
            p = p.next;
        }
        return p;
    }

    public ListNode middleNode(ListNode head) {
        int curIdx = 1, midIdx, count = 1;
        ListNode midNode = head, curNode = head;

        while (curNode.next != null) {
            curNode = curNode.next;
            count++;
            midIdx = count / 2 + 1;

            while (curIdx != midIdx) {
                midNode = midNode.next;
                curIdx++;
            }
        }

        return midNode;
    }

    public static void main(String[] args) {
        Problem876 p = new Problem876();

        System.out.println(p.middleNode(
                LeetcodeUtil.buildListNode(new int[] {1,2,3,4,5})).val);    // 3

        System.out.println(p.middleNode(
                LeetcodeUtil.buildListNode(new int[] {1,2,3,4,5,6})).val);  // 4

        System.out.println(p.middleNode(
                LeetcodeUtil.buildListNode(new int[] {1,2})).val);  // 2

        System.out.println(p.middleNode(
                LeetcodeUtil.buildListNode(new int[] {1,2,3})).val);// 2

        System.out.println(p.middleNode(
                LeetcodeUtil.buildListNode(new int[] {1})).val);    // 1
    }
}
