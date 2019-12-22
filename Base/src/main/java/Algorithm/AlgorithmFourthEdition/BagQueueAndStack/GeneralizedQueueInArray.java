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
public class GeneralizedQueueInArray<K> {

    private K[] values;
    private int size;

    public GeneralizedQueueInArray() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public GeneralizedQueueInArray(int initCapacity) {
        values = (K[]) new Object[initCapacity];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @SuppressWarnings("unchecked")
    public void insert(K item) {
        if (size == values.length) {
            K[] temp = values;
            values = (K[]) new Object[2 * size];
            System.arraycopy(temp, 0, values, 0, size);
        }
        values[size] = item;
        size++;
    }

    public K delete(int index) {
        index = index - 1;
        if (index <0 || index >= size)
            throw new IllegalArgumentException("out of size");

        K temp = values[index];
        values[index] = null;
        while (index <= size - 2) {
            values[index] = values[index + 1];
            index++;
        }

        size--;
        return temp;
    }

    public static void main(String[] args) {
        GeneralizedQueueInArray<String> queue = new GeneralizedQueueInArray<>(4);
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
