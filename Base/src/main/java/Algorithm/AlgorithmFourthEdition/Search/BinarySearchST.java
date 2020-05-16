package Algorithm.AlgorithmFourthEdition.Search;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by user-hfc on 2020/4/29.
 *
 * 基于有序数组的二分查找
 * 暂不处理数组扩容
 */
public class BinarySearchST<k extends Comparable<k>, v>
        implements OrderedST<k, v> {

    /**
     * 3.1.12 修改BinarySearchST，用一个Item对象的数组而非两个平行
     * 数组来保存键和值。添加一个构造函数，接受一个Item的数组为参数并
     * 将其归并排序。
     */
    private Item<k, v>[] items;
    private int size;

    public BinarySearchST() {}

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        items = new Item[capacity];
    }

    @SuppressWarnings("unchecked")
    public BinarySearchST(Item[] source) {
        size = source.length;
        items = new Item[size];
        mergeSort(source, 0, size/2, size-1);
    }

    protected void mergeSort(Item<k, v>[] source, int lo, int mid, int hi) {
        if (lo >= hi)
            return;

        mergeSort(source, lo, (lo+mid)/2, mid);
        mergeSort(source, mid+1, (mid+1+hi)/2, hi);

        int i=lo, j=mid+1;
        for (int k=lo; k<=hi;) {
            if (i > mid)
                items[k++] = source[j++];
            else if (j > hi)
                items[k++] = source[i++];
            else if (source[i].key.compareTo(source[j].key) <= 0)
                items[k++] = source[i++];
            else
                items[k++] = source[j++];
        }
        System.arraycopy(items, lo, source, lo, hi-lo+1);
    }

    @Override
    public k min() {
        return items[0].key;
    }

    @Override
    public k max() {
        return items[size - 1].key;
    }

    /**
     * 3.1.17 为BinarySearchST实现floor()方法
     */
    @Override
    public k floor(k key) {
        for (int i=0; i<size; i++) {
            if (key.compareTo(items[i].key) == 0)
                return key;
            else if (key.compareTo(items[i].key) < 0)
                return i == 0 ? null : items[i-1].key;
        }
        return items[size-1].key;
    }

    @Override
    public k ceiling(k key) {
        int i = rank(key);
        return items[i].key;
    }

    // 找到了就返回相应的数组下标值，未找到就返回0
    @Override
    public int rank(k key) {
        int lo = 0, hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = key.compareTo(items[mid].key);
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
        return items[index].key;
    }

    @Override
    public Iterable<k> keys(k lo, k hi) {
        Queue<k> q = new Queue<>();
        for (int i=rank(lo); i<rank(hi); i++)
            q.put(items[i].key);
        if (contains(hi))
            q.put(items[rank(hi)].key);
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
                    items[i] = null;
                    size--;
                } else {
                    items[i] = items[i+1];
                }
            }
            return;
        }

        if (i < size && items[i].key.compareTo(key) == 0) {
            items[i].val = value;
            return;
        }

        for (int j = size; j > i; j--) {
            items[j] = items[j-1];
        }
        items[i] = new Item(key, value);
        size++;
    }

    @Override
    public v get(k key) {
        if (isEmpty())
            return null;
        int i = rank(key);
        if (i < size && items[i].key.compareTo(key) == 0)
            return items[i].val;
        else
            return null;
    }

    /**
     * 3.1.16 为BinarySearchST实现delete()方法
     */
    @Override
    public void delete(k key) {
        if (key == null)
            return;

        for (int i=0; i<size; i++) {
            if (key.compareTo(select(i)) == 0) {
                for (int j=i; j<=size-1; j++) {
                    if (j == size - 1)
                        items[j] = null;
                    else
                        items[j] = items[j+1];
                }
                size--;
                break;
            }
        }
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

        System.out.println();
        System.out.println("---test 3.1.12---");
        System.out.println();

        Random random = new Random();
        Item[] ts = new Item[content.length];
        for (int i=0; i<content.length; i++) {
            ts[i] = new Item<>(String.valueOf(random.nextInt(50)), content[i]);
        }
        BinarySearchST<String, String> bss = new BinarySearchST<>(ts);
        for (Item<String, String> item : bss.items) {
            System.out.println(item.key + " : " + item.val);
        }
    }

    static class Item<k, v> {

        protected k key;
        protected v val;

        public Item(k k, v v) {
            this.key = k;
            this.val = v;
        }
    }
}
