package Algorithm.AlgorithmFourthEdition.String;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/7/11.
 *
 * 基于单词查找树的符号表
 */
public class TrieST<V> implements StringST<V> {

    private static int R = 256;     // 符号表基数（ASCII码）
    private Node<V> root;
    private int size;

    @SuppressWarnings("unchecked")
    private static class Node<VALUE> {
        private VALUE val;
        private Node<VALUE>[] next = (Node<VALUE>[]) new Node[R];
    }

    @Override
    public void put(String key, V val) {
        root = put(root, key, val, 0);
    }

    private Node<V> put(Node<V> x, String key, V val, int d) {
        // 如果key存在于以x为根结点的子单词查找树中则更新与它相关联的值
        boolean newNode = false;
        if (x == null) {
            x = new Node<>();
            newNode = true;
        }
        if (d == key.length()) {
            x.val = val;
            if (newNode)    // 既创建了新结点，又更新了值
                size++;
            return x;
        }

        char c = key.charAt(d); // 找到第d个字符所对应的子单词查找树
        x.next[c] = put(x.next[c], key, val, d+1);
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
        // 返回以x作为根结点的子单词查找树中与key相关联的值
        if (x == null)
            return null;
        if (d == key.length())
            return x;

        char c = key.charAt(d); // 找到第d个字符所对应的子单词查找树
        return get(x.next[c], key, d+1);
    }

    @Override
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node<V> delete(Node<V> x, String key, int d) {
        if (x == null)
            return null;
        if (d == key.length()) {
            if (x.val != null)  // 删除原先有值的情况才减一
                size--;
            x.val = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if (x.val != null)
            return x;

        for (char c=0; c<R; c++) {
            if (x.next[c] != null)
                return x;
        }
        return null;
    }

    @Override
    public boolean contains(String key) {
        Queue<String> q = new Queue<>();
        collect(root, "", key, q);
        return q.size() != 0;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    @Override
    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    /**
     * 通配符匹配开头
     */
    @Override
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<>();
        collect(root, "", pat, q);
        return q;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /**
     * 从结点x开始，收集所有存在的键
     */
    private void collect(Node<V> x, String pre, Queue<String> q) {
        if (x == null)
            return;
        if (x.val != null)
            q.put(pre);
        for (char c=0; c<R; c++)
            collect(x.next[c], pre+c, q);
    }

    /**
     * 从结点x开始，收集符合pat模式的键
     */
    private void collect(Node<V> x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null)
            return;
        if (d == pat.length() && x.val != null)
            q.put(pre);
        if (d == pat.length())
            return;

        char next = pat.charAt(d);
        for (char c=0; c<R; c++) {
            if (next == '.' || next == c)
                collect(x.next[c], pre+c, pat, q);
        }
    }

    private int search(Node<V> x, String s, int d, int length) {
        if (x == null)
            return length;
        if (x.val != null)
            length = d;
        if (d == s.length())
            return length;

        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);
    }

    public static void main(String[] args) {
        StringST<Integer> sst = new TrieST<>();
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
