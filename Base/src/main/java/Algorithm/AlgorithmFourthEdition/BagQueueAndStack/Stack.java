package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Iterator;

/**
 * Created by hfc on 2019/12/8.
 * 1.3 背包、队列和栈
 */
public class Stack<K> implements Iterable<K> {

    private Node first;
    private int size;

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(K item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    public K pop() {
        if (size == 0)
            return null;

        K item = first.item;
        first = first.next;
        size--;
        return item;
    }

    // 返回最近添加的元素
    public K peek() {
        if (null != first)
            return first.item;
        else
            return null;
    }

    @Override
    public Iterator<K> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<K> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public K next() {
            K item = current.item;
            current = current.next;
            return item;
        }
    }

    private class Node {
        K item;
        Node next;
    }


    public static void main(String[] args) {
        String mockInput = "to be or not to - be - - that - - - is a problem";
        String[] mockInputArray = mockInput.split(" ");

        Stack<String> s = new Stack<>();
        for (String item : mockInputArray) {
            if (!"-".equals(item))
                s.push(item);
            else if (!s.isEmpty())
                System.out.print(s.pop() + " ");
        }

        System.out.println();
        System.out.println("There is " + s.size() + " left on Stack, there is:");
        Iterator<String> iterator = s.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next() + " ");
        System.out.println();
    }
}
