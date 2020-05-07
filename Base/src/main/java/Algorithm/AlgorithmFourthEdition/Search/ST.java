package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/4/28.
 */
public interface ST<k, v> {

    /**
     * 将键值对存入表中
     * 若值为空则将键key从表中删除
     */
    void put(k key, v value);

    /**
     * 获取键key对应的值
     * 若键key不存在则返回null
     */
    v get(k key);

    /**
     * 从表中删去键key
     * 及其对应的值
     */
    default void delete(k key) {
        put(key, null);
    }

    /**
     * 键key在表中是否有对应的值
     */
    default boolean contains(k key) {
        return get(key) != null;
    }

    /**
     * 表是否为空
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 表中的键值对数量
     */
    int size();

    /**
     * 表中的所有键的集合
     */
    Iterable<k> keys();
}
