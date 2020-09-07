package Algorithm.Leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user-hfc on 2020/9/7.
 *
 * 347. 前 K 个高频元素
 *
 * 给定一个非空的整数数组，返回其中出现频率前 k 高的元素。
 *
 * 示例 1:
 * 输入: nums = [1,1,1,2,2,3], k = 2
 * 输出: [1,2]
 *
 * 示例 2:
 * 输入: nums = [1], k = 1
 * 输出: [1]
 *  
 * 提示：
 * 你可以假设给定的 k 总是合理的，且 1 ≤ k ≤ 数组中不相同的元素的个数。
 * 你的算法的时间复杂度必须优于 O(n log n) , n 是数组的大小。
 * 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的。
 * 你可以按任意顺序返回答案。
 *
 */
public class Problem347 {

    public int[] topKFrequent(int[] nums, int k) {
        // TODO: 2020/9/7 使用索引优先队列来优化方案
        return solution1(nums, k);
    }

    private int[] solution1(int[] nums, int k) {
        Map<Integer, Integer> oMap = new HashMap<>();
        Integer key;
        for (int i=0; i<nums.length; i++) {
            key = nums[i];
            if (oMap.containsKey(key)) oMap.put(key, oMap.get(key) + 1);
            else oMap.put(key, 1);
        }

        Node head = null;
        for (Map.Entry<Integer, Integer> entry : oMap.entrySet()) {
            head = buildQueue(head, entry.getValue(), entry.getKey());
        }

        int[] ret = new int[k];
        Node cur = head;
        for (int i=0; i<k; i++) {
            // 按题意，无具体值时k=0，因此这里不会出现空指针
            ret[i] = cur.val;
            cur = cur.next;
        }

        return ret;
    }

    private Node buildQueue(Node head, int count, int value) {
        Node newNode = new Node(count, value);
        if (head == null)
            return newNode;

        Node cur = head, prev = null;
        while (cur != null) {
            if (cur.cnt >= count) {
                prev = cur;
                cur = cur.next;
            } else if (cur == head) {
                newNode.next = head;
                head = newNode;
                break;
            } else {
                prev.next = newNode;
                newNode.next = cur;
                break;
            }
        }

        if (cur == null)
            prev.next = newNode;

        return head;
    }

    class Node {
        int cnt;
        int val;
        Node next;

        public Node (int cnt, int val) {
            this.cnt = cnt;
            this.val = val;
        }
    }

    public static void main(String[] args) {
        Problem347 p = new Problem347();

        int[] nums = new int[]{1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.print("[");
        for (int i : p.topKFrequent(nums, k))
            System.out.print(i + ",");
        System.out.println("]");

        System.out.println("---------------");

        nums = new int[]{1};
        k = 1;
        System.out.print("[");
        for (int i : p.topKFrequent(nums, k))
            System.out.print(i + ",");
        System.out.println("]");

        System.out.println("---------------");

        nums = new int[]{};
        k = 0;
        System.out.print("[");
        for (int i : p.topKFrequent(nums, k))
            System.out.print(i + ",");
        System.out.println("]");

        System.out.println("---------------");

        nums = new int[]{1, 1, 2, 3, 3, 3, 4, 4, 5, 5, 6};
        k = 5;
        System.out.print("[");
        for (int i : p.topKFrequent(nums, k))
            System.out.print(i + ",");
        System.out.println("]");

        System.out.println("---------------");

        nums = new int[]{3, 0, 1, 0};
        k = 1;
        System.out.print("[");
        for (int i : p.topKFrequent(nums, k))
            System.out.print(i + ",");
        System.out.println("]");
    }
}
