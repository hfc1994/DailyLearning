package Algorithm.AlgorithmFourthEdition.Search;

import java.util.Iterator;

/**
 * Created by user-hfc on 2020/6/9.
 *
 * 3.5.1 分别使用ST和HashST来实现SET和HashSET（为键关联虚拟值并忽略它们）
 *
 * 用有序符号表RedBlackBST来实现SET，
 * 用有序性操作无关紧要且拥有散列函数的LinearProbingHashST来实现HashSET
 */
public class SET<k extends Comparable<k>> {

    private RedBlackBST<k, Object> bst;
    private Object obj = new Object();

    public SET() {
        this.bst = new RedBlackBST<>();
    }

    public void add(k key) {
        bst.put(key, obj);
    }

    public void delete(k key) {
        bst.delete(key);
    }

    public boolean contains(k key) {
        return bst.contains(key);
    }

    public boolean isEmpty() {
        return bst.isEmpty();
    }

    public int size() {
        return bst.size();
    }
}
