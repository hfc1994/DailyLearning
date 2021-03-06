package Algorithm.AlgorithmFourthEdition.Search;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by user-hfc on 2020/6/9.
 *
 * 3.5.4 分别为int和double两种原始数据类型的键实现HashSTint和HashSTdouble类（
 * 将LinearProbingHashST中泛型改为原始数据类型）。
 */
public class HashSTdouble<v> {

    private int N;  // 符号表中键值对的总数
    private int M; // 线性探测表的大小
    private double[] keys;   // 键
    private v[] vals;   // 值

    public HashSTdouble() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public HashSTdouble(int cap) {
        keys = new double[cap];
        vals = (v[]) new Object[cap];
        M = cap;
    }

    public void put(double key, v val) {
        if (N >= M/2)
            resize(2 * M);  // 将M加倍

        int i;
        for (i = hash(key); vals[i] != null; i = (i+1)%M) {
            if (keys[i] == key) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public v get(double key) {
        for (int i = hash(key); vals[i] != null; i = (i+1)%M) {
            if (keys[i] == key)
                return vals[i];
        }
        return null;
    }

    public void delete(double key) {
        if (!contains(key))
            return;

        int i = hash(key);
        while (key != keys[i])
            i = (i + 1) % M;

        keys[i] = 0;
        vals[i] = null;
        i = (i + 1) % M;
        // 因为在put()里，容量达一半就会扩容
        // 所以这里不会出现死循环
        while (vals[i] != null) {
            double keyToRedo = keys[i];
            v valToRedo = vals[i];
            keys[i] = 0;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N == M/8)
            resize(M / 2);
    }

    public boolean contains(double key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return N;
    }

    public Iterable<Double> keys() {
        return () -> new Iterator<Double>() {

            private int takeIndex = 0;  // 已经获取的个数
            private int current = 0;    // 当前指向

            @Override
            public boolean hasNext() {
                return takeIndex != N;
            }

            @Override
            public Double next() {
                while (vals[current] == null)
                    current++;
                takeIndex++;
                return keys[current++];
            }
        };
    }

    private int hash(double key) {
        return ((Double) key).hashCode() & 0x7fffffff % M;
    }

    private void resize(int cap) {
        HashSTdouble<v> t = new HashSTdouble<>(cap);
        for (int i=0; i<M; i++) {
            if (vals[i] != null)
                t.put(keys[i], vals[i]);
        }
        keys = t.keys;
        vals = t.vals;
        M = cap;
    }

    public static void main(String[] args) {
        HashSTdouble<String> hsd = new HashSTdouble<>();
        Random r = new Random();
        for (int i=0; i<20; i++)
            hsd.put(r.nextDouble() * 500D, "abc");

        System.out.println("----");
        System.out.println(hsd.size());
        Iterator<Double> ir = hsd.keys().iterator();
        double temp = 0D;
        while (ir.hasNext()) {
            temp = ir.next();
            System.out.print(temp + " ");
        }
        System.out.println();
        hsd.delete(temp);
        System.out.println(hsd.size());
    }
}
