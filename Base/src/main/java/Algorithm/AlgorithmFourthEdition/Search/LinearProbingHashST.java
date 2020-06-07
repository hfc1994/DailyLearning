package Algorithm.AlgorithmFourthEdition.Search;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by user-hfc on 2020/6/6.
 *
 * 基于线性探测的散列表
 */
public class LinearProbingHashST<k,v> implements ST<k,v> {

    private int N;  // 符号表中键值对的总数
    private int M; // 线性探测表的大小
    private k[] keys;   // 键
    private v[] vals;   // 值

    @SuppressWarnings("unchecked")
    public LinearProbingHashST() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashST(int cap) {
        keys = (k[]) new Object[cap];
        vals = (v[]) new Object[cap];
        M = cap;
    }

    @Override
    public void put(k key, v val) {
        if (N >= M/2)
            resize(2 * M);  // 将M加倍

        int i;
        for (i = hash(key); keys[i] != null; i = (i+1)%M) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    @Override
    public v get(k key) {
        for (int i = hash(key); keys[i] != null; i = (i+1)%M) {
            if (keys[i].equals(key))
                return vals[i];
        }
        return null;
    }

    @Override
    public void delete(k key) {
        if (!contains(key))
            return;

        int i = hash(key);
        while (!key.equals(keys[i]))
            i = (i + 1) % M;

        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;
        // 因为在put()里，容量达一半就会扩容
        // 所以这里不会出现死循环
        while (keys[i] != null) {
            k keyToRedo = keys[i];
            v valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M/8)
            resize(M / 2);
    }

    @Override
    public int size() {
        return N;
    }

    /**
     * 3.4.19 为SeparateChainingHashST和LinearProbingHashST实现keys()方法
     */
    @Override
    public Iterable<k> keys() {
        return () -> new Iterator<k>() {

            private int takeIndex = 0;  // 已经获取的个数
            private int current = 0;    // 当前指向

            @Override
            public boolean hasNext() {
                return takeIndex != N;
            }

            @Override
            public k next() {
                while (keys[current] == null)
                    current++;
                takeIndex++;
                return keys[current++];
            }
        };
    }

    private int hash(k key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int cap) {
        LinearProbingHashST<k,v> t = new LinearProbingHashST<>(cap);
        for (int i=0; i<M; i++) {
            if (keys[i] != null)
                t.put(keys[i], vals[i]);
        }
        keys = t.keys;
        vals = t.vals;
        M = cap;
    }

    public static void main(String[] args) {
        LinearProbingHashST<Integer, String> lphs = new LinearProbingHashST<>();
        Random r = new Random();
        for (int i=0; i<20; i++)
            lphs.put(r.nextInt(500), "abc");

        System.out.println("----");
        System.out.println(lphs.size());
        Iterator<Integer> ir = lphs.keys().iterator();
        while (ir.hasNext())
            System.out.print(ir.next() + " ");
        System.out.println();
    }
}
