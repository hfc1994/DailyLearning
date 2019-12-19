package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Iterator;

/**
 * Created by user-hfc on 2019/12/19.
 * 
 * 1.3.35 编写一个用例，使用RandomQueue<Poker>在斗地主中发牌（两人17张，一人20张）
 * 
 * 注：仅为了考虑方便发牌，暂不考虑比较大小
 */
public class Poker implements Iterable<Poker> {
    
    private static String[] colors = {"红桃", "黑桃", "方片", "梅花"};
    private static String[] numbers = {"2", "3", "4", "5", "6", "7",
            "8", "9", "10", "J", "Q", "K", "A"};
    private static String[] specials = {"大王", "小王"};
    
    // 实际的扑克牌
    private String value;
    public String getValue() {
        return value;
    }

    // FIXME: 2019/12/20 这个迭代器是实例的迭代器 
    @Override
    public Iterator<Poker> iterator() {
        return new pokerMaker();
    }
    
    private class pokerMaker implements Iterator<Poker> {
        @Override
        public boolean hasNext() {
            // TODO: 2019/12/20   
            return false;
        }

        @Override
        public Poker next() {
            return null;
        }
    }
}
