package Algorithm.AlgorithmFourthEdition.Search;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/4/29.
 *
 * 基于无序链表的顺序查找
 */
public class SequentialSearchST<k, v> implements ST<k, v> {

    // 链表首节点
    private Node first;

    // 节点个数
    private int size;

    @Override
    public void put(k key, v value) {
        // 值为null时，删除键对应的节点
        // 查找给定的键，找到则更新其值，否则在表中新建节点
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.val = value;
                return;
            }
        }

        first = new Node(key, value, first);    //  未命中，新建节点
        size++;
    }

    @Override
    public v get(k key) {
        // 查找给定的键，返回相关联的值
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;   // 命中
        }
        return null;    // 未命中
    }


    /**
     * 3.1.5 实现SequentialSearchST中的size()、delete()和keys()方法。
     */
    @Override
    public void delete(k key) {
        Node prev = null;
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                // 删除当前节点
                if (prev == null) first = x.next;
                else prev.next = x.next;

                x.next = null;
                size--;
                return;
            }
            prev = x;
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<k> keys() {
        return () -> new Iterator<k>() {

            private Node current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public k next() {
                k ret = current.key;
                current = current.next;
                return ret;
            }
        };
    }

    public static void main(String[] args) {
        String[] content = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk"};
        SequentialSearchST<String, String> sss = new SequentialSearchST<>();
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

    // 链表节点定义
    private class Node {
        k key;
        v val;
        Node next;

        public Node(k key, v val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }
}
