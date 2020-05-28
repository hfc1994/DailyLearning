package Algorithm.AlgorithmFourthEdition.Search;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

/**
 * Created by user-hfc on 2020/5/21.
 *
 * 红黑树
 */
public class RedBlackBST<k extends Comparable<k>, v>
        extends BST<k, v> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

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
        if (root == null)
            return null;

        Node x = root;
        while (x.right != null)
            x = x.right;

        return x.key;
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

    @Override
    public v get(k key) {
        if (key == null)
            return null;

        return get(root, key);
    }

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
    public int size() {
        return size(root);
    }

    public int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    /**
     * 以下开始是红黑树的独特实现
     */

    @Override
    public void put(k key, v val) {
        // 查找key，找到则更新其值，否则为它新建一个结点
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, k key, v val) {
        // 标准的插入操作，和父结点用红链接相连
        if (h == null)
            return new Node(key, val, 1, RED);

        int cmp = key.compareTo(h.key);
        if (cmp < 0)
            h.left = put(h.left, key, val);
        else if (cmp > 0)
            h.right = put(h.right, key, val);
        else
            h.val = val;

        // 右侧出现红链接
        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);

        // 左侧出现连续的红链接
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);

        // 左右子树均为红链接
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 删除操作相关
     */
    private Node moveRedLeft(Node h) {
        // 假设结点h为红色，h.left和h.left.left都是黑色
        // 将h.left或者h.left的子结点之一变红
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }

    private Node moveRedRight(Node h) {
        // 假设结点h为红色，h.right和h.right.left都是黑色
        // 将h.right或者h.right的子结点之一变红
        flipColors(h);
        if (!isRed(h.left.left))
            h = rotateRight(h);
        return h;
    }

    private Node balance(Node h) {
        if (isRed(h.right)) h = rotateLeft(h);

        // 右侧出现红链接
        if (isRed(h.right) && !isRed(h.left))
            h = rotateLeft(h);

        // 左侧出现连续的红链接
        if (isRed(h.left) && isRed(h.left.left))
            h = rotateRight(h);

        // 左右子树均为红链接
        if (isRed(h.left) && isRed(h.right))
            flipColors(h);

        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    @Override
    public void delete(k key) {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = delete(root, key);
        if (!isEmpty())
            root.color = BLACK;
    }

    private Node delete(Node h, k key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                h.val = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, key);
            }
        }
        return balance(h);
    }

    @Override
    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMin(root);
        if (!isEmpty())
            root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null)
            return null;
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    @Override
    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;
        root = deleteMax(root);
        if (!isEmpty())
            root.color = BLACK;
    }

    private Node deleteMax(Node h) {
        if (isRed(h.left))
            h = rotateRight(h);
        if (h.right == null)
            return null;
        if (!isRed(h.right) && !isRed(h.right.left))
            h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }

    public static void main(String[] args) {
        String[] content = new String[]{"e","a","s","y","q","u","e","s","t","i","o","n"};

        RedBlackBST<String, String> rbb = new RedBlackBST<>();
        for (int i=0; i<content.length; i++) {
            rbb.put(content[i], String.valueOf(i));
        }

        System.out.println(rbb.min());
        System.out.println(rbb.max());
    }

    private class Node {
        k key;      // 键
        v val;    // 相关联的值
        Node left, right;   // 左右子树
        int N;      // 这课子树中的结点总数
        boolean color;  // 其父结点指向它的链接的颜色

        Node(k key, v val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }
}
