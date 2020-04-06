package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Node;
import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/6.
 *
 * 2.4.3 用以下数据结构实现优先队列，支持插入元素和删除最大元素的操作：
 * 无序数组、有序数组、无序链表和链表。
 *
 * 有序链表
 * 简单实现，不考虑数据边界问题
 */
public class OrderListPQ<T extends Comparable> implements PQ<T> {

    private Node<T> pq;
    private int N = 0;
    private Node<T> tail;

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public void insert(T v) {
        Node<T> newNode = new Node<>();
        newNode.value = v;

        if (pq == null) {
            pq = newNode;
            tail = pq;
        } else {
            Node<T> target = tail;
            while (target != null) {
                if (target.value.compareTo(newNode.value) < 0)
                    break;
                target = target.prev;
            }

            if (target == tail) {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            } else if (target == null) {
                newNode.next = pq;
                pq.prev = newNode;
                pq = newNode;
            } else {
                newNode.prev = target;
                newNode.next = target.next;
                if (target.next != null)
                    target.next.prev = newNode;
                target.next = newNode;
            }
        }
        N++;
    }

    @Override
    public T delMax() {
        T ret = tail.value;
        if (tail.prev != null)
            tail.prev.next = null;

        tail = tail.prev;

        N--;
        return ret;
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        PQ<Integer> maxIntPQ = new OrderListPQ<>();
        for (int data : src)
            maxIntPQ.insert(data);

        int[] dst = new int[src.length];
        while (!maxIntPQ.isEmpty())
            dst[src.length - maxIntPQ.size()] = maxIntPQ.delMax();

        Utils.showDescResult(dst);
    }
}
