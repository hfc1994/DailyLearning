package Algorithm.AlgorithmFourthEdition.PriorityQueue;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Node;
import Algorithm.AlgorithmFourthEdition.Utils;

/**
 * Created by user-hfc on 2020/4/6.
 *
 * 2.4.3 用以下数据结构实现优先队列，支持插入元素和删除最大元素的操作：
 * 无序数组、有序数组、无序链表和链表。
 *
 * 无序链表
 * 简单实现，不考虑数据边界问题
 */
public class DisorderListPQ<T extends Comparable> implements PQ<T> {

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
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        N++;
    }

    @Override
    public T del() {
        Node<T> max = pq;
        Node<T> p = pq;
        while (p.next != null) {
            p = p.next;
            if (max.value.compareTo(p.value) < 0) {
                max = p;
            }
        }

        T ret = max.value;
        if (max.prev != null)
            max.prev.next = max.next;
        if (max.next != null)
            max.next.prev = max.prev;
        N--;

        return ret;
    }

    public static void main(String[] args) {
        int[] src = Utils.numGen(20);

        PQ<Integer> maxIntPQ = new DisorderListPQ<>();
        for (int data : src)
            maxIntPQ.insert(data);

        int[] dst = new int[src.length];
        while (!maxIntPQ.isEmpty())
            dst[src.length - maxIntPQ.size()] = maxIntPQ.del();

        Utils.showDescResult(dst);
    }
}
