package Algorithm.AlgorithmFourthEdition.Search;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/4/29.
 *
 * 基于有序数组的二分查找
 * 暂不处理数组扩容
 */
public class BinarySearchST<k extends Comparable<k>, v>
        implements OrderedST<k, v> {

    private k[] keys;
    private v[] vals;
    private int size;

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        keys = (k[]) new Comparable[capacity];
        vals = (v[]) new Object[capacity];
    }

    @Override
    public k min() {
        return keys[0];
    }

    @Override
    public k max() {
        return keys[size - 1];
    }

    @Override
    public k floor(k key) {
        int i = rank(key);
        return keys[i];
    }

    @Override
    public k ceiling(k key) {
        int i = rank(key);
        return keys[i];
    }

    // 找到了就返回相应的数组下标值，未找到就返回0
    @Override
    public int rank(k key) {
        int lo = 0, hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
    }

    @Override
    public k select(int index) {
        return keys[index];
    }

    @Override
    public Iterable<k> keys(k lo, k hi) {
        Queue<k> q = new Queue<>();
        for (int i=rank(lo); i<rank(hi); i++)
            q.put(keys[i]);
        if (contains(hi))
            q.put(keys[rank(hi)]);
        return q;
    }

    @Override
    public void put(k key, v value) {

        // 查找键，找到则更新值，否则创建新的元素
        int i = rank(key);

        // value为null，则删除key
        if (value == null) {
            for (; i<size; i++) {
                if (i == size - 1) {
                    keys[i] = null;
                    vals[i] = null;
                    size--;
                } else {
                    keys[i] = keys[i+1];
                    vals[i] = vals[i+1];
                }
            }
            return;
        }

        if (i < size && keys[i].compareTo(key) == 0) {
            vals[i] = value;
            return;
        }

        for (int j = size; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key;
        vals[i] = value;
        size++;
    }

    @Override
    public v get(k key) {
        if (isEmpty())
            return null;
        int i = rank(key);
        if (i < size && keys[i].compareTo(key) == 0)
            return vals[i];
        else
            return null;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        String[] content = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk"};
        BinarySearchST<String, String> sss = new BinarySearchST<>(10);
        for (int i=0; i<content.length; i++) {
            sss.put(String.valueOf(i % 10), content[i]);
        }

        System.out.println("sss size = " + sss.size());
        System.out.println("==========");
        Iterator<String> it = sss.keys().iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("key = " + key);
            System.out.println("value = " + sss.get(key));
            System.out.println("-------");
        }

        for (int i=0; i<content.length; i++) {
            sss.delete(String.valueOf(i % 10));
            System.out.println("size = " + sss.size());
        }
        System.out.println("isEmpty = " + sss.isEmpty());
    }
}
