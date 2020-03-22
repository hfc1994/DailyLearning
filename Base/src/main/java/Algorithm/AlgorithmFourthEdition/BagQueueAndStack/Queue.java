package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/22.
 *
 * 1.3.41 复制队列。编写一个新搭构造函数，使用以下代码
 * Queue<Item> r = new Queue<Item>(q);
 * 得到的r指向队列q的一个新的独立的副本。可以对q或r进行任意入列或出列操作但它们
 * 不会相互影响。提示：从q中取出所有元素再将它们插入q和r
 *
 */
public class Queue<K> {

    private Node<K> head;
    private Node<K> tail;
    private int size;

    public Queue() {}

    public Queue(Queue<K> others) {
        Queue<K> exchange = new Queue<>();
        K temp;
        while ((temp = others.take()) != null)
            exchange.put(temp);

        while ((temp = exchange.take()) != null) {
            others.put(temp);
            this.put(temp);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void put(K value) {
        Node<K> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public K take() {
        if (size == 0)
            return null;

        Node<K> temp = head;
        if (head == tail) {
            head = null;
            tail = head;
        } else {
            head = head.next;
            temp.next.prev = null;
            temp.next = null;
        }

        size--;
        return temp.value;
    }

    public K peek() {
        if (size == 0)
            return null;
        return head.value;
    }

    public static void main(String[] args) {
//        Queue<String> queue = new Queue<>();
//        queue.put("aaa");
//        queue.put("bbb");
//        queue.put("ccc");
//        System.out.println(queue.take());
//        System.out.println(queue.take());
//        System.out.println(queue.take());
//        System.out.println(queue.take());

        Queue<String> q = new Queue<>();
        q.put("aaa");
        q.put("bbb");
        q.put("ccc");
        q.put("ddd");

        Queue<String> r = new Queue<>(q);
        String str;
        while ((str = q.take()) != null)
            System.out.print(str + " ");
        System.out.println();

        while ((str = r.take()) != null)
            System.out.print(str + " ");
        System.out.println();
    }
}
