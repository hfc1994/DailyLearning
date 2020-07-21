package Algorithm.AlgorithmFourthEdition.String;

/**
 * Created by user-hfc on 2020/7/11.
 *
 * 以字符串为键的符号表的API
 */
public interface StringST<V> {

    /**
     * 向表中插入键值对（如果值为null则删除键key）
     */
    void put(String key, V val);

    /**
     * 键key所对应的值（如果键不存在则返回null）
     */
    V get(String key);

    /**
     * 删除键key（和它的值）
     */
    void delete(String key);

    /**
     * 表中是都保存着key的值
     */
    boolean contains(String key);

    /**
     * 符号表是否为空
     */
    boolean isEmpty();

    /**
     * s的前缀中最长的键
     */
    String longestPrefixOf(String s);

    /**
     * 所有以s为前缀的键
     */
    Iterable<String> keysWithPrefix(String s);

    /**
     * 所有和s匹配的键（其中“.”能够匹配任意字符）
     */
    Iterable<String> keysThatMatch(String s);

    /**
     * 键值对的数量
     */
    int size();

    /**
     * 符号表中的所有键
     */
    Iterable<String> keys();
}
