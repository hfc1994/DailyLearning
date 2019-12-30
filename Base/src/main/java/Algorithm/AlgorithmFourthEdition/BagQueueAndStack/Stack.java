package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Created by hfc on 2019/12/8.
 * 1.3 背包、队列和栈
 */
public class Stack<K> implements Iterable<K> {

    protected Node<K> first;
    protected int size;

    /**
     * 1.3.50 快速出错的迭代器。修改Stack的迭代器代码，确保一旦用例在迭代器中
     * （通过push()或pop()操作）修改集合数据就抛出一个
     * java.util.ConcurrentModificationException异常。
     */
    private int modCount;

    public Stack() {}

    /**
     * 1.3.42 复制栈。为基于链表实现的栈编写一个新的构造函数，使以下代码
     * Stack<Item> t = new Stack<Item>(s);
     * 得到的t指向栈s的一个新的独立的副本。
     */
    public Stack(Stack<K> s) {
        Stack<K> exchange = new Stack<>();
        K temp;
        while ((temp = s.pop()) != null)
            exchange.push(temp);

        while ((temp = exchange.pop()) != null) {
            s.push(temp);
            this.push(temp);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(K value) {
        Node<K> oldFirst = first;
        first = new Node<>();
        first.value = value;
        first.next = oldFirst;
        size++;
        modCount++;
    }

    public K pop() {
        if (size == 0)
            return null;

        K value = first.value;
        first = first.next;
        size--;
        modCount++;
        return value;
    }

    // 返回最近添加的元素
    public K peek() {
        if (null != first)
            return first.value;
        else
            return null;
    }

    /**
     * 1.3.12 编写一个可迭代的Stack用例，它包含一个静态的copy()方法，接受一个
     * 字符串的栈作为参数并返回该栈的一个副本。
     */
    public static Stack<String> copy(Stack<String> source) {
        Iterator<String> iterator = source.iterator();
        Stack<String> transfer = new Stack<>();
        while (iterator.hasNext())
            transfer.push(iterator.next());

        Stack<String> target = new Stack<>();
        while (transfer.size() != 0)
            target.push(transfer.pop());

        return target;
    }

    @Override
    public Iterator<K> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<K> {

        private Node<K> current = first;
        private int exceptModCount = modCount;

        @Override
        public boolean hasNext() {
            if (exceptModCount != modCount)
                throw new ConcurrentModificationException();

            return current != null;
        }

        @Override
        public K next() {
            if (exceptModCount != modCount)
                throw new ConcurrentModificationException();

            K item = current.value;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
//        String mockInput = "to be or not to - be - - that - - - is a problem";
//        String[] mockInputArray = mockInput.split(" ");
//
//        Stack<String> s = new Stack<>();
//        for (String item : mockInputArray) {
//            if (!"-".equals(item))
//                s.push(item);
//            else if (!s.isEmpty())
//                System.out.print(s.pop() + " ");
//        }
//
//        System.out.println();
//        System.out.println("There is " + s.size() + " left on Stack, there is:");
//        Iterator<String> iterator = s.iterator();
//        while (iterator.hasNext())
//            System.out.print(iterator.next() + " ");
//        System.out.println();
//
//        System.out.println("-------------------------------------");
//
//        Stack<String> copy = Stack.copy(s);
//        System.out.println("The copy Stack: ");
//        Iterator<String> iterator2 = copy.iterator();
//        while (iterator2.hasNext())
//            System.out.print(iterator2.next() + " ");


        Stack<String> s = new Stack<>();
        s.push("aaa");
        s.push("bbb");
        s.push("ccc");
        s.push("ddd");

        Stack<String> t = new Stack<>(s);
        String temp;
        while ((temp = s.pop()) != null)
            System.out.print(temp + " ");
        System.out.println();

        while ((temp = t.pop()) != null)
            System.out.print(temp + " ");
        System.out.println();
    }
}
