package Algorithm.AlgorithmFourthEdition.Search;

import java.util.Date;

/**
 * Created by user-hfc on 2020/6/6.
 *
 * 3.4.25 散列值的缓存。修改3.4.1.8节的Transaction类并维护一个变量
 * hash，在hashCode()方法第一次为一个对象计算散列值后将值保存在hash
 * 中，这样随后的调用就不必重新计算了。
 * 请注意：这种方法仅适用于不可变的数据类型。
 *
 */
public class Transaction {
    private final String who;
    private final Date when;
    private final double amount;

    private int hashCode = -1;

    public Transaction(String who, Date when, double amount) {
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    public int hashCode() {
        if (hashCode > 0)
            return hashCode;

        int hash = 17;
        hash = 31 * hash + who.hashCode();
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((Double) amount).hashCode();

        hashCode = hash;
        return hashCode;
    }
}
