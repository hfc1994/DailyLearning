package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/6/9.
 *
 * 3.5.1 分别使用ST和HashST来实现SET和HashSET（为键关联虚拟值并忽略它们）
 *
 * 用有序符号表RedBlackBST来实现SET，
 * 用有序性操作无关紧要且拥有散列函数的LinearProbingHashST来实现HashSET
 */
public class HashSET<k> {

    private LinearProbingHashST<k, Object> hashST;
    private Object obj = new Object();

    public HashSET() {
        this(16);
    }

    public HashSET(int initSize) {
        hashST = new LinearProbingHashST<>(initSize);
    }

    public void add(k key) {
        hashST.put(key, obj);
    }

    public void delete(k key) {
        hashST.delete(key);
    }

    public boolean contains(k key) {
        return hashST.contains(key);
    }

    public boolean isEmpty() {
        return hashST.isEmpty();
    }

    public int size() {
        return hashST.size();
    }
}
