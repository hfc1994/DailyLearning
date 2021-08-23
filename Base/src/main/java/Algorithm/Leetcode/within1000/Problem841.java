package Algorithm.Leetcode.within1000;

import java.util.*;

/**
 * Created by hfc on 2020/9/1.
 *
 * 841. 钥匙和房间
 *
 * 有 N 个房间，开始时你位于 0 号房间。每个房间有不同的号码：0，1，2，...，N-1，并且房间里可能有一些钥匙能使你进入下一个房间。
 * 在形式上，对于每个房间 i 都有一个钥匙列表 rooms[i]，每个钥匙 rooms[i][j] 由 [0,1，...，N-1] 中的一个整数表示，其中 N = rooms.length。
 * 钥匙 rooms[i][j] = v 可以打开编号为 v 的房间。
 * 最初，除 0 号房间外的其余所有房间都被锁住。
 * 你可以自由地在房间之间来回走动。
 * 如果能进入每个房间返回 true，否则返回 false。
 *
 * 示例 1：
 * 输入: [[1],[2],[3],[]]
 * 输出: true
 * 解释:
 * 我们从 0 号房间开始，拿到钥匙 1。
 * 之后我们去 1 号房间，拿到钥匙 2。
 * 然后我们去 2 号房间，拿到钥匙 3。
 * 最后我们去了 3 号房间。
 * 由于我们能够进入每个房间，我们返回 true。
 *
 * 示例 2：
 * 输入：[[1,3],[3,0,1],[2],[0]]
 * 输出：false
 * 解释：我们不能进入 2 号房间。
 *
 * 提示：
 * 1 <= rooms.length <= 1000
 * 0 <= rooms[i].length <= 1000
 * 所有房间中的钥匙数量总计不超过 3000。
 *
 */
public class Problem841 {

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // 使用自带的BlockQueue严重影响速度
        Queue<Integer> queue = new LinkedList<>();
//        Queue<Integer> queue = new Queue<>();
        Boolean[] appear = new Boolean[rooms.size()];

        appear[0] = Boolean.TRUE;
        scanNum(rooms.get(0), appear, queue);
        int count = 1 + queue.size();

        while (!queue.isEmpty()) {
            int init = queue.size();
            scanNum(rooms.get(queue.poll()), appear, queue);
            count = count + queue.size() - init + 1;
        }

        return count == rooms.size();
    }

    private void scanNum(List<Integer> keys, Boolean[] appear, Queue<Integer> queue) {
        // 外层使用forEach来遍历也会轻微影响速度
        for (int i=0; i<keys.size(); i++) {
            Integer key = keys.get(i);
            if (appear[key] == null) {
                appear[key] = Boolean.TRUE;
                queue.add(key);
            }
        }
    }


//    class Queue<K>{
//
//        private Node<K> head;
//        private Node<K> tail;
//        private int size;
//
//        public Queue() {}
//
//        public boolean isEmpty() {
//            return size == 0;
//        }
//
//        public int size() {
//            return size;
//        }
//
//        public void put(K value) {
//            Node<K> newNode = new Node<>(value);
//            if (head == null) {
//                head = newNode;
//                tail = newNode;
//            } else {
//                tail.next = newNode;
//                tail = newNode;
//            }
//            size++;
//        }
//
//        public K take() {
//            if (size == 0)
//                return null;
//
//            Node<K> temp = head;
//            if (head == tail) {
//                head = null;
//                tail = head;
//            } else {
//                head = head.next;
//                temp.next = null;
//            }
//
//            size--;
//            return temp.value;
//        }
//
//    }
//
//    class Node<K> {
//
//        public K value;
//
//        public Node<K> next;
//
//        public Node() {}
//
//        public Node(K value) {
//            this.value = value;
//        }
//
//        public Node(K value, Node<K> next) {
//            this.value = value;
//            this.next = next;
//        }
//    }

    public static void main(String[] args) {
        Problem841 p = new Problem841();

        List<List<Integer>> rooms = new ArrayList<>();
        rooms.add(Arrays.asList(1));
        rooms.add(Arrays.asList(2));
        rooms.add(Arrays.asList(3));
        rooms.add(Arrays.asList());
        System.out.println(p.canVisitAllRooms(rooms));

        rooms = new ArrayList<>();
        rooms.add(Arrays.asList(1, 3));
        rooms.add(Arrays.asList(3, 0, 1));
        rooms.add(Arrays.asList(2));
        rooms.add(Arrays.asList(0));
        System.out.println(p.canVisitAllRooms(rooms));
    }
}
