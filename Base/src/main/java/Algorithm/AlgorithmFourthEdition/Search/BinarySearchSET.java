package Algorithm.AlgorithmFourthEdition.Search;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by user-hfc on 2020/6/9.
 *
 * 3.5.3 删除BinarySearchST中和值相关的所有代码来实现BinarySearchSET
 */
public class BinarySearchSET<k extends Comparable<k>> {

    private Item<k>[] items;
    private int size;

    public BinarySearchSET() {}

    @SuppressWarnings("unchecked")
    public BinarySearchSET(int capacity) {
        items = new Item[capacity];
    }

    @SuppressWarnings("unchecked")
    public BinarySearchSET(Item[] source) {
        size = source.length;
        items = new Item[size];
        mergeSort(source, 0, size/2, size-1);
    }

    protected void mergeSort(Item<k>[] source, int lo, int mid, int hi) {
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

    public k min() {
        return items[0].key;
    }

    public k max() {
        return items[size - 1].key;
    }

    public k floor(k key) {
        for (int i=0; i<size; i++) {
            if (key.compareTo(items[i].key) == 0)
                return key;
            else if (key.compareTo(items[i].key) < 0)
                return i == 0 ? null : items[i-1].key;
        }
        return items[size-1].key;
    }

    public k ceiling(k key) {
        int i = rank(key);
        return items[i].key;
    }

    // 找到了就返回相应的数组下标值，未找到就返回0
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

    public k select(int index) {
        return items[index].key;
    }

    public Iterable<k> keys() {
        return keys(min(), max());
    }

    public Iterable<k> keys(k lo, k hi) {
        Queue<k> q = new Queue<>();
        for (int i=rank(lo); i<rank(hi); i++)
            q.put(items[i].key);
        if (contains(hi))
            q.put(items[rank(hi)].key);
        return q;
    }

    public void put(k key) {

        // 查找键，找到则更新值，否则创建新的元素
        int i = rank(key);

        if (i < size && items[i].key.compareTo(key) == 0) {
            return;
        }

        for (int j = size; j > i; j--) {
            items[j] = items[j-1];
        }
        items[i] = new Item<>(key);
        size++;
    }

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

    public boolean contains(k key) {
        int i = rank(key);
        if (i != 0)
            return true;
        else return items[0].key.equals(key);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    static class Item<k> {

        protected k key;

        public Item(k k) {
            this.key = k;
        }
    }

    public static void main(String[] args) {
        String[] content = new String[]{"aa", "bb", "cc", "dd", "ee", "aa", "gg", "dd", "ii", "ee", "kk"};
        BinarySearchSET<String> set = new BinarySearchSET<>(10);
        for (int i=0; i<content.length; i++) {
            set.put(content[i]);
        }

        System.out.println("sss size = " + set.size());
        System.out.println("==========");
        Iterator<String> it = set.keys().iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("key = " + key);
            System.out.println("-------");
        }

        for (int i=0; i<content.length; i++) {
            set.delete(content[i]);
            System.out.println("size = " + set.size());
        }
        System.out.println("isEmpty = " + set.isEmpty());
    }
}
