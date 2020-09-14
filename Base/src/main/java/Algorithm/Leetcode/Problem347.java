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
//        return solution1(nums, k);
        return solution2(nums, k);
    }

    private int[] solution1(int[] nums, int k) {
        Map<Integer, Integer> oMap = new HashMap<>();
        Integer key;
        for (int i=0; i<nums.length; i++) {
            key = nums[i];
            oMap.put(key, oMap.getOrDefault(key, 0) + 1);
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

    // 索引优先队列
    private int[] solution2(int[] nums, int k) {
        // 有很多重复值，来一个激进点的初始值
        MaxPQ maxPQ = new MaxPQ(nums.length);
        for (int i=0; i<nums.length; i++)
            maxPQ.insertByVal(nums[i]);

        int[] ret = new int[k];
        for (int i=0; i<k; i++)
            ret[i] = maxPQ.delMax().val;
        return ret;
    }

    class MaxPQ {

        Node[] pq;
        int size;

        public MaxPQ (int initSize) {
            pq = new Node[initSize + 1];
            size = 0;
        }

        public void insert(Node node) {
            // 数组首位空置的
//            if (size + 1 >= pq.length) {
//                Node[] tmp = pq;
//                pq = new Node[2 * (pq.length - 1)];
//                System.arraycopy(tmp, 1, pq, 1, size);
//            }
            pq[++size] = node;
            swim(size);
        }

        public void insertByVal(int val) {
            if (size == 0) {
                insert(new Node(1, val));
                return;
            }

            boolean inserted = false;
            for (int i=1; i<=size; i++) {
                if (pq[i].val == val) {
                    pq[i].cnt = pq[i].cnt + 1;
                    inserted = true;
                    swim(i);
                    break;
                }
            }

            if (!inserted)
                insert(new Node(1, val));
        }

        public Node delMax() {
            if (size > 0) {
                Node max = pq[1];
                pq[1] = pq[size--];
                sink(1);
                return max;
            }
            return null;
        }

        // 上浮
        private void swim(int index) {
            int tIndex;
            while (index > 1) {
                tIndex = index / 2;
                if (pq[tIndex].cnt < pq[index].cnt) {
                    exchangeNode(index, tIndex);
                    index = tIndex;
                    continue;
                }
                break;
            }
        }

        // 下沉
        private void sink(int index) {
            int tIndex;
            while (index <= size/2) {
                tIndex = 2 * index;
                if (tIndex+1 <= size && pq[tIndex+1].cnt > pq[tIndex].cnt)
                    tIndex++;

                if (pq[index].cnt < pq[tIndex].cnt) {
                    exchangeNode(tIndex, index);
                    index = tIndex;
                    continue;
                }
                break;
            }
        }

        private void exchangeNode(int from, int to) {
            Node tmp = pq[from];
            pq[from] = pq[to];
            pq[to] = tmp;
        }
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

//        int[] nums = new int[] {1, 3, 6, 5, 7, 2, 8, 4};
//        MaxPQ maxPq = p. new MaxPQ(8);
//        for (int i : nums) {
//            maxPq.insert(p. new Node(i, i));
//        }
//
//        while (maxPq.size != 0)
//            System.out.println(maxPq.delMax().val);

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

        nums = new int[]{1, 1, 2, 3, 3, 3, 4, 4, 5, 5, 6, 7, 8, 9, 10};
        k = 6;
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
