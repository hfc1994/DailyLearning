package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 背包
 */
public class Bag<k> implements Iterable<k> {

    private Node<k> head;
//    private Node<k> tail;
    private int size;

    public void add(k item) {
        if (head == null)
            head = new Node<>(item);
        else
            head = new Node<>(item, head);

        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<k> iterator() {
        return new Iterator<k>() {

            private Node<k> index = head;

            @Override
            public boolean hasNext() {
                return index != null;
            }

            @Override
            public k next() {
                k ret = index.value;
                index = index.next;
                return ret;
            }
        };
    }

    public static void main(String[] args) {
        Bag<String> bag = new Bag<>();

        for (int i=0; i<10; i++)
            bag.add(String.valueOf(i));

        System.out.println(bag.size);
        Iterator<String> ir = bag.iterator();
        while (ir.hasNext())
            System.out.print(ir.next() + " ");
        System.out.println();
    }
}
