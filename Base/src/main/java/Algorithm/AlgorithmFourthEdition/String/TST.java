package Algorithm.AlgorithmFourthEdition.String;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/7/12.
 *
 * 基于三向单词查找树的符号表
 */
public class TST<V> implements StringST<V> {

    private Node<V> root;                  // 树的根结点
    private int size;

    private class Node<VALUE> {
        char c;                         // 字符
        /**
         * left和right相对于当前节点是平级关系
         * mid相对于当前节点是递进关系
         */
        Node<VALUE> left, mid, right;   // 左中右子三向单词查找树
        VALUE val;                      // 和字符串相关联的值
    }

    @Override
    public void put(String key, V val) {
        root = put(root, key, val, 0);
    }

    private Node<V> put(Node<V> x, String key, V val, int d) {
        char c = key.charAt(d);
        boolean newNode = false;
        if (x == null) {
            x = new Node<>();
            x.c = c;
            newNode = true;
        }

        if (c < x.c)
            x.left = put(x.left, key, val, d);
        else if (c > x.c)
            x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid, key, val, d+1);
        else {
            if (newNode)
                size++;
            x.val = val;
        }
        return x;
    }

    @Override
    public V get(String key) {
        Node<V> x = get(root, key, 0);
        if (x == null)
            return null;
        return x.val;
    }

    private Node<V> get(Node<V> x, String key, int d) {
        if (x == null)
            return null;
        if (d == key.length())
            return x;

        char c = key.charAt(d);
        if (c < x.c)
            return get(x.left, key, d);
        else if (c > x.c)
            return get(x.right, key, d);
        else if (d < key.length() - 1)
            return get(x.mid, key, d+1);
        else
            return x;
    }

    @Override
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node<V> delete(Node<V> x, String key, int d) {
        if (x == null)
            return null;

        char c = key.charAt(d);
        if (c < x.c)
            x.left = delete(x.left, key, d);
        else if (c > x.c)
            x.right = delete(x.right, key, d);
        else if (d < key.length() - 1) {
            x.mid = delete(x.mid, key, d+1);
        } else {
            size--;
            if (x.left == null && x.mid == null && x.right == null)
                return null;
            else
                x.val = null;
        }

        return x;
    }

    @Override
    public boolean contains(String key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String longestPrefixOf(String key) {
        int length = search(root, key, 0);
        return key.substring(0, length);
    }

    @Override
    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<>();
        Node<V> preNode = get(root, pre, 0);
        if (preNode != null) {
            if (preNode.val != null)
                q.put(pre);
            collect(preNode.mid, pre, q);
        }
        return q;
    }

    @Override
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<>();
        collect(root, "", pat, 0, q);
        return q;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<String> keys() {
        Queue<String> q = new Queue<>();
        collect(root, "", q);
        return q;
    }

    private int search(Node<V> x, String key, int d) {
        if (x == null)
            return d;

        char c = key.charAt(d);
        if (c < x.c)
            return search(x.left, key, d);
        else if (c > x.c)
            return search(x.right, key, d);
        else if (d < key.length() - 1)
            return search(x.mid, key, d+1);
        return d;
    }

    /**
     * 从结点x开始，收集所有存在的键
     */
    private void collect(Node<V> x, String pre, Queue<String> q) {
        if (x == null)
            return;
        if (x.val != null)
            q.put(pre + x.c);

        collect(x.left, pre, q);
        collect(x.mid, pre + x.c, q);
        collect(x.right, pre, q);
    }

    /**
     * 从结点x开始，收集所有符合pat的键
     */
    private void collect(Node<V> x, String pre, String pat, int d, Queue<String> q) {
        if (x == null)
            return;
        if (d == pat.length() - 1) {
            if (x.val != null)
                q.put(pre + x.c);
            return;
        }

        char c = pat.charAt(d);

        if (c < x.c || c == '.')
            collect(x.left, pre, pat, d, q);

        if (c > x.c || c == '.')
            collect(x.right, pre, pat, d, q);

        if (c == x.c || c == '.')
            collect(x.mid, pre + x.c, pat, d+1, q);
    }

    public static void main(String[] args) {
        StringST<Integer> sst = new TST<>();
        sst.put("by", 0);
        sst.put("sea", 1);
        sst.put("sells", 2);
        sst.put("she", 3);
        sst.put("shells", 4);
        sst.put("shore", 5);
        sst.put("the", 6);

        System.out.println("except null, actual: " + sst.get("sell"));
        System.out.println("except 2, actual: " + sst.get("sells"));
        System.out.println("except false, actual: " + sst.contains("see"));
        System.out.println("except true, actual: " + sst.contains("the"));
        System.out.println("except 7, actual: " + sst.size());
        System.out.println("except false, actual: " + sst.isEmpty());

        System.out.println("except 'she', actual: " + sst.longestPrefixOf("sheaaa"));
        System.out.println("keys with prefix of 'sh': ");
        Iterator<String> it1 = sst.keysWithPrefix("sh").iterator();
        while (it1.hasNext())
            System.out.println("--- " + it1.next());
        System.out.println();

        System.out.println("keys that match pattern of '.he': ");
        Iterator<String> it2 = sst.keysThatMatch(".he").iterator();
        while (it2.hasNext())
            System.out.println("--- " + it2.next());
        System.out.println();

        sst.delete("shore");
        sst.delete("shc");
        System.out.println("except 6, actual: " + sst.size());

        System.out.println("all keys: ");
        Iterator<String> it3 = sst.keys().iterator();
        while (it3.hasNext())
            System.out.println("--- " + it3.next());
    }
}
