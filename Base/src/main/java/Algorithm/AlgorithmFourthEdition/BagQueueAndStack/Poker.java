package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by user-hfc on 2019/12/19.
 *
 * 1.3.35 编写一个用例，使用RandomQueue<Poker>在斗地主中发牌（两人17张，一人20张）
 *
 * 注：仅为了考虑方便发牌，暂不考虑比较大小
 */
public class Poker {

    private static String[] colors = {"红桃", "黑桃", "方片", "梅花"};
    private static String[] numbers = {"2", "3", "4", "5", "6", "7",
            "8", "9", "10", "J", "Q", "K", "A"};
    private static String[] specials = {"大王", "小王"};
    public static String[] PokerBox;

    static {
        PokerBox = new String[colors.length * numbers.length + specials.length];
        int index = 0;
        for (String color : colors) {
            for (String number : numbers) {
                PokerBox[index] = color + " " + number;
                index++;
            }
        }
        PokerBox[index++] = specials[0];
        PokerBox[index] = specials[1];
    }

    // 实际的扑克牌
    private String value;

    public Poker(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static Iterator<Poker> shuffle() {
        return new shuffle();
    }

    private static class shuffle implements Iterator<Poker> {

        private Random random;
        private int[] seq;
        private int size;

        public shuffle() {
            random = new Random(System.currentTimeMillis());
            size = PokerBox.length;
            seq = new int[PokerBox.length];
            for (int i=0; i<size; i++)
                seq[i] = i;
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Poker next() {
            int i = random.nextInt(size);
            int index = seq[i];
            seq[i] = seq[--size];
            seq[size] = -1;
            return new Poker(PokerBox[index]);
        }
    }

    public static void main(String[] args) {
        Iterator<Poker> shuffle = Poker.shuffle();
        while (shuffle.hasNext())
            System.out.println(shuffle.next().getValue());
    }
}
