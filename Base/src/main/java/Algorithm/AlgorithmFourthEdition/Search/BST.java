package Algorithm.AlgorithmFourthEdition.Search;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

/**
 * Created by user-hfc on 2020/5/16.
 *
 * 基于二叉查找树的符号表
 */
public class BST<k extends Comparable<k>, v> implements OrderedST<k, v> {

    protected Node root;  // 二叉查找树的根结点

    /**
     * 查找key，找到则更新它的值，否则为它创建一个新的结点
     */
    @Override
    public void put(k key, v val) {
        root = put(root, key, val);
    }

    /**
     * 如果key存在于以x为根结点的子树中则更新它的值
     * 否则将以key和val为键值对的新结点插入到该子树中
     */
    private Node put(Node x, k key, v val) {
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);

        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public v get(k key) {
        return get(root, key);
    }

    /**
     * 在以x为根结点的子树中查找并返回key所对应的值
     * 如果找不到则返回null
     */
    private v get(Node x, k key) {
        if (x == null)
            return null;

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    @Override
    public void delete(k key) {
        root = delete(root, key);
    }

    private Node delete(Node x, k key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;

            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }

    @Override
    public k min() {
        return root == null ? null : min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null)
            return x;
        else
            return min(x.left);
    }

    @Override
    public k max() {
        return root == null ? null : max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null)
            return x;
        else
            return max(x.right);
    }

    @Override
    public k floor(k key) {
        Node x = floor(root, key);
        return x == null ? null : x.key;
    }

    private Node floor(Node x, k key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
            return floor(x.left, key);

        Node t = floor(x.right, key);
        if (t != null)
            return t;
        else
            return x;
    }

    @Override
    public k ceiling(k key) {
        return ceiling(root, key).key;
    }

    private Node ceiling(Node x, k key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
            return ceiling(x.right, key);

        Node t = floor(x.left, key);
        if (t != null)
            return t;
        else
            return x;
    }

    @Override
    public int rank(k key) {
        return rank(root, key);
    }

    /**
     * 返回以x为根结点的子树中小于key的键的数量
     */
    private int rank(Node x, k key) {
        if (x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return rank(x.left, key);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(x.right, key);
        else
            return size(x.left);
    }

    @Override
    public k select(int index) {
        return select(root, index).key;
    }

    // 返回排名为k的结点
    private Node select(Node x, int k) {
        if (x == null)
            return null;
        int t = size(x.left);
        if (t > k)
            return select(x.left, k);
        else if (t < k)
            return select(x.right, k-t-1);
        else
            return x;
    }

    @Override
    public Iterable<k> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<k> keys(k lo, k hi) {
        Queue<k> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<k> queue, k lo, k hi) {
        if (x == null)
            return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0)
            keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0)
            queue.put(x.key);
        if (cmphi > 0)
            keys(x.right, queue, lo, hi);
    }

    public static void main(String[] args) {
        String[] content = new String[]{"e","a","s","y","q","u","e","s","t","i","o","n"};

        BST<String, String> bstnr = new BST<>();
        for (int i=0; i<content.length; i++) {
            bstnr.put(content[i], String.valueOf(i));
        }

        System.out.println("min is " + bstnr.min());
        System.out.println("max is " + bstnr.max());
        System.out.println("floor x = " + bstnr.floor("x"));
        System.out.println("ceiling x = " + bstnr.ceiling("x"));
        System.out.println("rank a = " + bstnr.rank("a"));
        System.out.println("select 3 = " + bstnr.select(3));
    }

    protected class Node {
        protected k key;
        protected v val;
        protected Node left, right;   // 左右子树
        protected int N;  // 以该结点为根的子树中的结点总数（包括当前结点）

        protected Node(k key, v val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }
}
