package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/15.
 *
 * 1.3.29 用环形链表实现Queue。环形链表也是一条链表，只是没有任何结点的链接为空，
 * 且只要链表非空则last.next的值为first。
 * 只能使用一个Node类型的实例变量（last）---这句话的意思应该是只有一个Node类型的last域？
 */
public class CircleQueue<K> {

    private Node<K> last;

    private int size;

    private int capacity;

    public Node<K> getLast() {
        return last;
    }

    public int getSize() {
        return size;
    }

    public CircleQueue(int initCapacity) {
        // 简单处理，不进行严谨判断值是否有效
        this.capacity = initCapacity;
    }

    public void add(K element) {
        Node<K> newNode = new Node<>(element);
        if (last == null) {
            newNode.prev = newNode;
            newNode.next = newNode;
            last = newNode;
        } else if (last.next == last) {
            // 即只有一个结点
            last.next = newNode;
            newNode.prev = last;
            last.prev = newNode;
            newNode.next = last;
        } else {
            Node<K> tail = last.prev;
            tail.next = newNode;
            newNode.prev = tail;

            // first向前移一位，相当于当队列满的时候
            // 再添加新元素就会覆盖最老的元素
            if (size == capacity) {
                Node<K> discardNode = last;
                last = last.next;
                discardNode.prev = null;
                discardNode.next = null;
            }

            newNode.next = last;
            last.prev = newNode;
        }

        if (size < capacity)
            size++;
    }

    // 队列是先进先出FIFO
    public K remove() {
        K ret;
        if (size == 1) {
            ret = last.value;
            last = null;
        } else {
            ret = last.value;
            Node<K> discardNode = last;
            last = last.next;
            discardNode.prev.next = last;
            last.prev = discardNode.prev;
            discardNode.next = null;
            discardNode.prev = null;
        }
        size--;
        return ret;
    }

    @Override
    public String toString() {
        if (last == null)
            return "[]";

        Node<K> current = last;
        StringBuilder sb = new StringBuilder("[");
        do {
            sb.append(current.value)
                    .append(",");
            current = current.next;
        } while (current != last);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        CircleQueue<String> queue = new CircleQueue<>(5);
        System.out.println("---empty queue---");
        System.out.println(queue.toString());
        queue.add("aaa");
        System.out.println("---after add one---");
        System.out.println(queue.toString());
        queue.add("bbb");
        System.out.println("---after add one---");
        System.out.println(queue.toString());
        queue.add("ccc");
        queue.add("ddd");
        queue.add("eee");
        queue.add("fff");
        queue.add("ggg");
        System.out.println("---after add to full and more---");
        System.out.println(queue.toString());
        String ret = queue.remove();
        System.out.println("---after remove : " + ret + "---");
        System.out.println(queue.toString());
        queue.add("hhh");
        queue.add("iii");
        System.out.println("---after add two---");
        System.out.println(queue.toString());
    }
}
