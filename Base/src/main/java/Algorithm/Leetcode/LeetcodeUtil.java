package Algorithm.Leetcode;

/**
 * Created by user-hfc on 2021/9/1.
 */
public class LeetcodeUtil {

    public static void printArray(int[] nums) {
        System.out.print("[");

        for (int i=0; i<nums.length; i++) {
            System.out.print(nums[i]);

            if (i != nums.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }

    public static void printArray(char[] chars) {
        System.out.print("[");

        for (int i=0; i<chars.length; i++) {
            System.out.print(chars[i]);

            if (i != chars.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }

    public static void print2DArray(int[][] arrays) {
        System.out.print("[");
        for (int i = 0; i < arrays.length; i++) {
            System.out.print("[");
            for (int j = 0; j < arrays[i].length; j++) {
                System.out.print(arrays[i][j]);
                if (j != arrays[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("]");
        }
        System.out.println("]");
    }

    public static ListNode buildListNode(int[] nums) {
        ListNode head = new ListNode(nums[0]);
        ListNode cur = head;
        for (int i = 1; i < nums.length; i++) {
            cur.next = new ListNode(nums[i]);
            cur = cur.next;
        }
        return head;
    }

    public static void printListNode(ListNode head) {
        System.out.print("[");
        while (head != null) {
            System.out.print(head.val);
            if (head.next != null) {
                System.out.print(", ");
            }
            head = head.next;
        }
        System.out.println("]");
    }
}
