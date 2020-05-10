package Algorithm.AlgorithmFourthEdition.Search;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by user-hfc on 2020/5/10.
 *
 * 3.1.3 开发一个符号表的实现OrderedSequentialSearchST，使用
 * 有序链表来实现我们的有序符号表API。
 *
 * 从小到大排序
 */
public class OrderedSequentialSearchST<k extends Comparable<k>, v>
        implements OrderedST<k, v> {

    // 链表首节点
    private Node head;
    // 链表尾节点
    private Node tail;

    // 节点个数
    private int size;

    @Override
    public k min() {
        if (head != null)
            return head.key;
        return null;
    }

    @Override
    public k max() {
        if (tail != null)
            return tail.key;
        return null;
    }

    @Override
    public k floor(k key) {

        Node index = head;
        k target = null;
        while (index != null && index.key.compareTo(key) <= 0) {
            target = index.key;
            index = index.next;
        }

        return target;
    }

    @Override
    public k ceiling(k key) {
        Node index = head;
        k target = null;
        while (index != null) {
            if (index.key.compareTo(key) >= 0) {
                target = index.key;
                break;
            }
            index = index.next;
        }

        return target;
    }

    @Override
    public int rank(k key) {
        Node index = head;
        int count = 0;
        while (index != null && index.key.compareTo(key) < 0) {
            count++;
            index = index.next;
        }

        return count;
    }

    @Override
    public k select(int index) {
        if (index > size || index == 0)
            return null;

        Node n = head;
        for (int i=1; i<index; i++)
            n = n.next;
        return n.key;
    }

    @Override
    public Iterable<k> keys(k lo, k hi) {
        Queue<k> q = new Queue<>();
        Node index = head;
        while (index != null && index.key.compareTo(hi) <= 0) {
            if (index.key.compareTo(lo) >= 0)
                q.put(index.key);
            index = index.next;
        }
        return q;
    }

    @Override
    public void put(k key, v value) {
        if (key == null || value == null)
            return;

        if (size == 0) { // 首个节点
            tail = head = new Node(key, value, null);
            size++;
        } else {
            Node index = head;
            Node before = null;
            while (index != null) {
                // 碰撞替换value
                if (index.key.compareTo(key) == 0) {
                    index.val = value;
                    break;
                }

                if (index.key.compareTo(key) > 0) {
                    if (head == index)   // 首节点
                        head = new Node(key, value, head);
                    else
                        before.next = new Node(key, value, before.next);
                    size++;
                    break;
                }

                before = index;
                index = index.next;
            }

            // 链表走到尾都没找到相等的key
            if (index == null) {
                tail.next = new Node(key, value, null);
                tail = tail.next;
                size++;
            }
        }
    }

    @Override
    public v get(k key) {
        if (head != null) {
            Node index = head;
            while (true) {
                if (index.key.compareTo(key) == 0)
                    return index.val;
                else if (index.key.compareTo(key) > 0)  // 从小到大排序的
                    break;
                index = index.next;
            }
        }
        return null;
    }

    @Override
    public void delete(k key) {
        if (key == null || head == null)
            return;

        Node index = head;
        Node before = null;
        while (index != null && index.key.compareTo(key) <= 0) {
            if (index.key.compareTo(key) == 0) {
                if (head == index) {
                    head = index.next;
                    index.next = null;
                } else if (tail == index) {
                    tail = before;
                    before.next = null;
                } else {
                    before.next = index.next;
                    index.next = null;
                }
                size--;
            }
            before = index;
            index = index.next;
        }
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        String[] content = new String[]{"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh", "ii", "jj", "kk"};
        OrderedSequentialSearchST<String, String> osss = new OrderedSequentialSearchST<>();
        for (int i=0; i<content.length; i++) {
            osss.put(String.valueOf(i % 10), content[i]);
        }

        System.out.println("osss size = " + osss.size());
        System.out.println("==========");
        Iterator<String> it = osss.keys().iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.println("key = " + key);
            System.out.println("value = " + osss.get(key));
            System.out.println("-------");
        }

        for (int i=0; i<content.length; i++) {
            osss.delete(String.valueOf(i % 10));
            System.out.println("size = " + osss.size());
        }
        System.out.println("isEmpty = " + osss.isEmpty());

        System.out.println("--------------------------");
        osss = new OrderedSequentialSearchST<>();
        Random random = new Random();
        for (int i=0; i<20; i++) {
            int kv = random.nextInt(100);
            osss.put(kv+"", kv+"");
            System.out.printf("input %3d -- size = %2d -- ", kv, osss.size);
            it = osss.keys().iterator();
            while (it.hasNext()) {
                String key = it.next();
                System.out.print("[" + key + ":" + osss.get(key) + "], ");
            }
            System.out.println();
        }

        System.out.println("min = " + osss.min());
        System.out.println("max = " + osss.max());
        System.out.println("floor 45 = " + osss.floor("45"));
        System.out.println("ceiling 55 = " + osss.ceiling("55"));
        System.out.println("select 7 = " + osss.select(7));
        System.out.println("rank index=7 = " + osss.rank(osss.select(7)));
        osss.delete(osss.select(7));
        System.out.println("delete index=7 made size = " + osss.size());
        it = osss.keys().iterator();
        while (it.hasNext()) {
            String key = it.next();
            System.out.print("[" + key + ":" + osss.get(key) + "], ");
        }
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
