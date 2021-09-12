package Algorithm.Leetcode;

/**
 * Created by user-hfc on 2021/9/4.
 */
public class ListNode {

    public int val;
    public ListNode next;

    public ListNode() {}

    public ListNode(int val) {
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static ListNode buildList(int[] list) {
        if (list == null || list.length == 0) return null;

        ListNode cur = new ListNode(list[0]);
        ListNode head = cur;

        for (int i = 1; i < list.length; i++) {
            cur.next = new ListNode(list[i]);
            cur = cur.next;
        }

        return head;
    }

}
