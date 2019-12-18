package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Iterator;

/**
 * Created by user-hfc on 2019/12/18.
 *
 * 1.3.34 随机背包，编写一个RandomBag类来实现API。请注意，除了形容词
 * 随机之外，这份API和Bag的API是相同的，这意味着迭代应该随机访问背包中
 * 的所有元素（对于每次迭代，所有的N!种排列出现的可能性均相同）。提示：
 * 用数组保存所有元素并在迭代器的构造函数中随机打乱它们的顺序。
 */
public class RandomBag<K> implements Iterable<K> {

    private K[] items;
    private int size;
    private int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public RandomBag() {
        items = (K[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public RandomBag(int initCapacity) {
        items = (K[]) new Object[initCapacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public void add(K item) {
        if (size == items.length) {
            K[] tmp  = items;
            items = (K[]) new Object[size * 2];
            System.arraycopy(tmp, 0, items, 0, size);
        }
        items[size++] = item;
    }

    @Override
    public Iterator<K> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<K> {

        private int[] seq = new int[size];
        private int index;

        public RandomIterator() {
            for (int i=0; i<seq.length; i++) {
                seq[i] = i;
            }
            shuffle(seq);
        }

        // 打乱数组里面的数据顺序
        private void shuffle(int[] array) {
            int len = array.length;
            int target;
            for (int i=0; i<len; i++) {
                target = (int) (Math.random() * len);
                int tmp = array[i];
                array[i] = array[target];
                array[target] = tmp;
            }
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public K next() {
            return items[seq[index++]];
        }
    }

    public static void main(String[] args) {
        RandomBag<String> bag = new RandomBag<>(4);
        bag.add("aa");bag.add("bb");bag.add("cc");bag.add("dd");
        bag.add("ee");bag.add("ff");bag.add("gg");bag.add("hh");
        bag.add("ii");bag.add("jj");bag.add("kk");bag.add("ll");
        System.out.println("isEmpty = " + bag.isEmpty());
        System.out.println("size = " + bag.size());
        Iterator<String> iterator = bag.iterator();
        System.out.println("---take from bag---");
        while (iterator.hasNext())
            System.out.print(iterator.next() + " ");
        System.out.println();
    }
}
