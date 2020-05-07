package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/4/28.
 */
public interface OrderedST<k extends Comparable<k>, v> extends ST<k, v> {

    /**
     * 最小的键
     */
    k min();

    /**
     * 最大的键
     */
    k max();

    /**
     * 小于等于key的最大键
     */
    k floor(k key);

    /**
     * 大于等于key的最小键
     */
    k ceiling(k key);

    /**
     * 小于key的键的数量
     */
    int rank(k key);

    /**
     * 排名为k的键
     */
    k select(int k);

    /**
     * 删除最小的键
     */
    default void deleteMin() {
        delete(min());
    }

    /**
     * 删除最大的键
     */
    default void deleteMax() {
        delete(max());
    }

    /**
     * [lo...hi]之间键的数量
     */
    default int size(k lo, k hi) {
        if (hi.compareTo(lo) < 0)
            return 0;
        else if (contains(hi))
            return rank(hi) - rank(lo) + 1;
        else
            return rank(hi) - rank(lo);
    }

    /**
     * [lo...hi]之间的所有键，已排序
     */
    Iterable<k> keys(k lo, k hi);

    /**
     * 表中的所有键的组合，已排序
     */
    @Override
    default Iterable<k> keys() {
        return keys(min(), max());
    }
}
