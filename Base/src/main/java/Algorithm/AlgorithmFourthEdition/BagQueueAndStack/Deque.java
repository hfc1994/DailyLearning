package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Iterator;

/**
 * Created by user-hfc on 2019/12/16.
 *
 * -----------------------------------------------------
 * public class Deque<Item> implements Iterable<Item>
 * -----------------------------------------------------
 *          Deque()                 创建空双向队列
 * boolean  isEmpty()               双向队列是否为空
 * int      size()                  双向队列中的元素数量
 * void     pushLeft(Item item)     向左端添加一个新元素
 * void     pushRight(Item item)    向右端添加一个新元素
 * Item     popLeft()               从左端删除一个元素
 * Item     popRight()              从右端删除一个元素
 * -----------------------------------------------------
 *
 * 1.3.33 Deque，一个双向队列（或者称为deque）和栈或队列相似，
 * 但它同时支持在两端添加或删除元素。编写一个使用双向链表实现这份
 * API的Dequq类
 * 以及一个使用动态数组调整实现这份API的ResizingArrayDeque类（个人觉得
 * 使用变长数组来实现这种Deque并不是很合理，所以就不打算实现。）
 */
public class Deque<K> implements Iterable<K> {

    private Node<K> head;

    private Node<K> tail;

    private int size;

    public Deque() {}

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void pushLeft(K value) {
        Node<K> newNode = new Node<>(value);
        if (tail == null) {
            tail = newNode;
            head = tail;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void pushRight(K value) {
        Node<K> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public K popLeft() {
        Node<K> popNode;
        if (tail == null)
            return null;

        popNode = tail;
        // 只有一个结点
        if (tail.prev == null) {
            tail = null;
            head = tail;
        } else {
            tail = tail.prev;
            tail.next = null;
            popNode.prev = null;
        }
        size--;
        return popNode.value;
    }

    public K popRight() {
        Node<K> popNode;
        if (head == null)
            return null;

        popNode = head;
        // 只有一个结点
        if (head.next == null) {
            head = null;
            tail = head;
        } else {
            head = head.next;
            head.prev = null;
            popNode.next = null;
        }
        size--;
        return popNode.value;
    }

    @Override
    public Iterator<K> iterator() {
        return new HeadIterator();
    }

    private class HeadIterator implements Iterator<K> {

        private Node<K> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public K next() {
            K ret = current.value;
            current = current.next;
            return ret;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println("Deque isEmpty:" + deque.isEmpty());
        System.out.println("Deque size:" + deque.size);

        deque.pushLeft("aaa");
        deque.pushLeft("bbb");
        System.out.println("DeQue popRight:" + deque.popRight());
        deque.pushRight("ccc");
        deque.pushRight("ddd");
        System.out.println("DeQue popLeft:" + deque.popLeft());

        System.out.println("Deque isEmpty:" + deque.isEmpty());
        System.out.println("Deque size:" + deque.size);

        Iterator<String> iterator1 = deque.iterator();
        while (iterator1.hasNext())
            System.out.print(iterator1.next() + " ");
        System.out.println();
        System.out.println("---over---");

        System.out.println("DeQue popLeft:" + deque.popLeft());
        System.out.println("DeQue popRight:" + deque.popRight());
        System.out.println("Deque isEmpty:" + deque.isEmpty());
        System.out.println("Deque size:" + deque.size);

        Iterator<String> iterator2 = deque.iterator();
        while (iterator2.hasNext())
            System.out.print(iterator2.next() + " ");
        System.out.println();
        System.out.println("---over---");
    }
}
