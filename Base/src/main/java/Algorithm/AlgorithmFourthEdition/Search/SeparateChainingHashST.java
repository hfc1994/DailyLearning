package Algorithm.AlgorithmFourthEdition.Search;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by user-hfc on 2020/6/3.
 *
 * 基于拉链法的散列表
 *
 * 3.4.2 重新实现SeparateChainingHashST，直接使用SequentialSearchST中链表部分的代码
 */
public class SeparateChainingHashST<k, v> implements ST<k, v> {

    private int N;  // 键值对总数
    private int M;  // 散列表的大小（数组长度）
    private Node<k, v>[] table;   // 存放Node对象的数组

    public SeparateChainingHashST() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public SeparateChainingHashST(int M) {
        // 创建M条链表
        this.M = M;
        table = new Node[M];
    }

    private int hash(k key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @Override
    public v get(k key) {
        // 查找给定的键，返回相关联的值
        Node<k, v> head = table[hash(key)];
        for (Node<k, v> x = head; x != null; x = x.next) {
            if (x.key.equals(key))
                return x.val;   // 命中
        }
        return null;    // 未命中
    }

    @Override
    public void delete(k key) {
        int index = hash(key);
        Node<k, v> head = table[index];
        Node<k, v> prev = null;
        for (Node<k, v> x = head; x != null;) {
            if (x.key.equals(key)) {
                if (prev == null)
                    table[index] = null;
                else {
                    prev.next = x.next;
                    x.next = null;
                }
                N--;
                return;
            }
            prev = x;
            x = x.next;
        }
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Iterable<k> keys() {
        return () -> new Iterator<k>() {

            private int index = 0;
            private int takeIndex = 0;  // 已经获取的个数
            private Node<k, v> current = table[index];

            @Override
            public boolean hasNext() {
                return takeIndex < N;
            }

            @Override
            public k next() {
                while (current == null) {
                    // 此时必然index<M-1
//                    if (index < M-1)
                    current = table[++index];
                }
                k ret = current.key;
                current = current.next;
                takeIndex++;
                return ret;
            }
        };
    }

    @Override
    public void put(k key, v val) {
        // 查找给定的键，找到则更新其值，否则在表中新建节点
        int index = hash(key);
        Node<k, v> head = table[index];
        for (Node<k, v> x = head; x != null; x = x.next) {
            if (x.key.equals(key)) {
                x.val = val;
                return;
            }
        }

        table[index] = new Node<>(key, val, head, N++);    //  未命中，新建节点
    }

    public void deleteByTs(int k) {
        List<k> keys = new ArrayList<>();
        int index = 0;
        int takeIndex = 1;  // 已经获取的个数
        Node<k, v> current = table[index];

        while (takeIndex <= N) {
            while (current == null)
                current = table[++index];

            if (current.ts > k)
                keys.add(current.key);

            current = current.next;
            takeIndex++;
        }

        keys.forEach(this::delete);
    }

    // 链表节点定义
    private class Node<KEY, VALUE> {
        KEY key;
        VALUE val;
        Node<KEY, VALUE> next;
        /**
         * 3.4.3 修改你为上一道练习给出的实现，为每一个键值对添加一个整型变量，
         * 将其值设为插入该键值对时散列表中元素的数量。实现一个方法，将该变量
         * 的值大于给定整数k的键（及其相应的值）全部删除。注意：这份额外的功能
         * 在为编译器实现符号表时很有用。
         */
        int ts;     // 节点插入时，散列表的大小

        public Node(KEY key, VALUE val, Node<KEY, VALUE> next, int tableSize) {
            this.key = key;
            this.val = val;
            this.next = next;
            this.ts = tableSize;
        }
    }

    public static void main(String[] args) {
        // 3.4.2
        SeparateChainingHashST<Integer, String> schs = new SeparateChainingHashST<>();
        Random r = new Random();
        for (int i=0; i<20; i++)
            schs.put(r.nextInt(500), "abc");

//        System.out.println(schs.size());
//        System.out.println(schs.get(1));
//        System.out.println("------");
//        Iterable<Integer> keys = schs.keys();
//        Iterator<Integer> ir = keys.iterator();
//        Integer key1 = ir.next();
//        System.out.println(key1);
//        System.out.println(schs.get(key1));
//        System.out.println("-------");
//        while (ir.hasNext()) {
//            System.out.print(ir.next());
//            System.out.print(" ");
//        }
//        System.out.println();
//        schs.delete(key1);
//        System.out.println(schs.size());
//        System.out.println(schs.get(key1));

        // 3.4.3
        System.out.println("--------");
        schs.deleteByTs(15);
        // TODO: 2020/6/5 为什么时而15时而16
        // FIXME: 2020/6/5 
        System.out.println(schs.size());
    }
}
