package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/22.
 *
 * 1.3.41 复制队列。编写一个新搭构造函数，使用以下代码
 * Queue<Item> r = new Queue<Item>(q);
 * 得到的r指向队列q的一个新的独立的副本。可以对q或r进行任意入列或出列操作但它们
 * 不会相互影响。提示：从q中取出所有元素再将它们插入q和r
 *
 * fixme 按题意理解的话，应该只能用链表的Queue来实现
 * fixme 队列先进先出，并且还需要能自动扩容
 *
 */
public class Queue<K> {

    private K[] values;
    private int size;
    private int head;
    private int tail;

    public Queue() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public Queue(int initCapacity) {
        values = (K[]) new Object[initCapacity];
        size = 0;
        head = tail = 0;
    }

    @SuppressWarnings("unchecked")
    public Queue(K[] others) {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public boolean offer(K item) {
        if (size == values.length)
            return false;

        values[head] = item;
        if (head == values.length - 1)
            head = 0;
        else
            head++;
        size++;
        return true;
    }

    @SuppressWarnings("unchecked")
    public K poll() {
        if (size == 0)
            return null;

        K temp = values[tail];
        if (tail == values.length - 1)
            tail = 0;
        else
            tail++;
        size--;
        return temp;
    }
}
