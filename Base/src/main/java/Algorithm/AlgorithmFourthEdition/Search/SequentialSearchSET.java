package Algorithm.AlgorithmFourthEdition.Search;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/6/9.
 *
 * 3.5.2 删除SequentialSearchST中和值相关的所有代码来实现SequentialSearchSET
 */
public class SequentialSearchSET<k> {

    // 链表首节点
    private Node first;

    // 节点个数
    private int size;

    public void add(k key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return;
            }
        }

        first = new Node(key, first);    //  未命中，新建节点
        size++;
    }

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

    public boolean contains(k key) {
        Node current = first;
        while (first != null) {
            if (first.key.equals(key))
                return true;
            first = first.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

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

    // 链表节点定义
    private class Node {
        k key;
        Node next;

        public Node(k key, Node next) {
            this.key = key;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        String[] content = new String[]{"aa", "bb", "cc", "dd", "aa", "ee", "ff", "gg", "bb", "ii", "dd", "kk"};
        SequentialSearchSET<String> set = new SequentialSearchSET<>();
        for (int i=0; i<content.length; i++) {
            set.add(content[i]);
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
