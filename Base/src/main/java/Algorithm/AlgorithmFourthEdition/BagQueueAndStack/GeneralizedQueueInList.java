package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/22.
 *
 * --------------------------------------------------------
 * public class GeneralizedQueue<Item>
 * --------------------------------------------------------
 *          GeneralizedQueue()  创建一个空队列
 * boolean  isEmpty()           队列是否为空
 * void     insert(Item x)      添加一个元素
 * Item     delete(int K)       删除并返回最早插入的第K个元素
 * --------------------------------------------------------
 *
 * 1.3.38 删除第K个元素。首先用数组实现该数据结构，然后用链表实
 * 现该数据结构。
 */
public class GeneralizedQueueInList<K> {

    private Node<K> head;
    private Node<K> current;
    private int size;

    public GeneralizedQueueInList() {}

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(K value) {
        Node<K> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            current = head;
        } else {
            current.next = newNode;
            current = newNode;
        }
        size++;
    }

    public K delete(int index) {
        if (size == 0)
            return null;

        Node<K> target = head;
        Node<K> prev = null;
        int targetIndex = 1;
        while (targetIndex != index) {
            prev = target;
            target = target.next;
            targetIndex++;
        }

        K ret = target.value;
        if (target == head)
            head = null;
        else
            prev.next = target.next;

        size--;
        return ret;
    }

    public static void main(String[] args) {
        GeneralizedQueueInList<String> queue = new GeneralizedQueueInList<>();
        queue.insert("aaa");
        queue.insert("bbb");
        queue.insert("ccc");
        queue.insert("ddd");
        queue.insert("eee");
        queue.insert("fff");
        queue.insert("ggg");
        queue.insert("hhh");
        queue.insert("iii");

        System.out.println(queue.delete(3));
        System.out.println(queue.delete(3));
        System.out.println(queue.delete(2));
        System.out.println(queue.delete(5));
        System.out.println(queue.size);

    }
}
