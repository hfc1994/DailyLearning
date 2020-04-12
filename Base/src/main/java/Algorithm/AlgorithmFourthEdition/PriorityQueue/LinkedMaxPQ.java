package Algorithm.AlgorithmFourthEdition.PriorityQueue;

/**
 * Created by user-hfc on 2020/4/12.
 *
 * 2.4.23 使用链表的优先队列。用堆有序的二叉树实现一个优先队列，
 * 但使用链表结构代替数组。每个结点都需要三个链接：两个向下，一个
 * 向上。你的实现即使在无法预知队列大小的情况下也能保证优先队列的
 * 基本操作所需的事件为对数级别。
 *
 * TODO 在不借助N的情况下实现
 */
public class LinkedMaxPQ<T extends Comparable> implements PQ<T> {

    private Node<T> head;
    private Node<T> tail;
    private int N;

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

    }

    @Override
    public T del() {
        return null;
    }

    //
    public static <T> void replaceNode(Node<T> targe, Node<T> replace) {

    }

    public static class Node<T> {

        public T value;
        public Node<T> prev;
        public Node<T> left;
        public Node<T> right;

        public Node(T v) {
            this.value = v;
        }
    }
}
