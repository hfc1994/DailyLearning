package Algorithm.Leetcode.pkg200;

/**
 * Created by hfc on 2022/1/20.
 *
 * 219. 存在重复元素 II
 *
 * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个不同
 * 的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。
 * 如果存在，返回 true ；否则，返回 false 。
 *
 * 示例 1：
 * 输入：nums = [1,2,3,1], k = 3
 * 输出：true
 *
 * 示例 2：
 * 输入：nums = [1,0,1,1], k = 1
 * 输出：true
 *
 * 示例 3：
 * 输入：nums = [1,2,3,1,2,3], k = 2
 * 输出：false
 *
 * 提示：
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 * 0 <= k <= 10^5
 *
 */
public class Problem219 {

    /**
     * 速度内存都较差
     */
    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 1; j <= k; j++) {
                int rIdx = i + j;
                if (rIdx < nums.length && nums[i] == nums[rIdx]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 速度内存都好一些
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums.length == 1) {
            return false;
        } else {
            int nodeLen = 2000;
            Node[] nodes = new Node[nodeLen];
            for (int i = nums.length - 1; i >= 0; i--) {
                int offset = (nums[i] + (int) Math.pow(10, 9)) % nodeLen;
                Node head = nodes[offset];
                if (head == null) {
                    nodes[offset] = new Node(i, nums[i]);
                } else if (this.matchOrAdd(head, i, nums[i], k)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean matchOrAdd(Node cur, int idx, int val, int k) {
        if (cur.val == val && cur.idx - idx <= k) return true;

        while (cur.next != null) {
            cur = cur.next;
            if (cur.val == val && cur.idx - idx <= k)
                return true;
        }
        cur.next = new Node(idx, val);
        return false;
    }

    class Node {
        int idx;
        int val;
        Node next;

        public Node(int idx, int val) {
            this.idx = idx;
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Problem219 p = new Problem219();

        System.out.println(!p.containsNearbyDuplicate(new int[] { 1 }, 1));
        System.out.println(!p.containsNearbyDuplicate(new int[] { 1 }, 2));
        System.out.println(p.containsNearbyDuplicate(new int[] { 1, 1 }, 1));
        System.out.println(p.containsNearbyDuplicate(new int[] { 1, 1 }, 2));
        System.out.println(p.containsNearbyDuplicate(new int[] { 1, 2, 3, 1 }, 3));
        System.out.println(p.containsNearbyDuplicate(new int[] { 1, 0, 1, 1 }, 1));
        System.out.println(!p.containsNearbyDuplicate(new int[] { 1, 2, 3, 1, 2, 3 }, 2));
    }

}
