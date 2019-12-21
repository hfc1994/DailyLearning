package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.*;

/**
 * Created by user-hfc on 2019/12/19.
 *
 * 1.3.35 随机队列，编写一个RandomQueue类来实现一份API。提示：使用（能够动态调整大小的）
 * 数组表示数据。删除一个元素时，随机交换某个元素（索引在0和N-1之间）和末位元素（索引为N-1）
 * 的位置，然后像ResizingArrayStack一样删除并返回末位元素。编写一个用例，使用RandomQueue<Poker>
 * 在斗地主中发牌（两人17张，一人20张）
 */
public class RandomQueue<K> implements Iterable<K> {

    private K[] items;
    private int size;
    private int DEFAULT_CAPACITY = 16;
    private Random random = new Random(System.currentTimeMillis());

    @SuppressWarnings("unchecked")
    public RandomQueue() {
        items = (K[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public RandomQueue(int initCapacity) {
        items = (K[]) new Object[initCapacity];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // 添加一个元素
    @SuppressWarnings("unchecked")
    public void enqueue(K item) {
        if (size == items.length) {
            K[] temp = items;
            items = (K[]) new Object[2 * size];
            System.arraycopy(temp, 0, items, 0, size);
        }
        items[size++] = item;
    }

    // 删除并随机返回一个元素（取样且不放回）
    public K dequeue() {
        int exchange = random.nextInt(size);
        K temp = items[exchange];
        items[exchange] = items[--size];
        items[size] = null;

        return temp;
    }

    // 随机返回一个元素但不删除它（取样且放回）
    public K sample() {
        int exchange = random.nextInt(size);
        K temp = items[exchange];
        items[exchange] = items[size - 1];
        items[size - 1] = temp;

        return temp;
    }

    @Override
    public Iterator<K> iterator() {
        return new RandomIterator();
    }

    /**
     * 1.3.36 随机迭代器。在1.3.35的基础上编写一个迭代器，随机返回队列中的所有元素。
     */
    private class RandomIterator implements Iterator<K> {

        private int[] seq;
        private int psize;
        private Random random;

        public RandomIterator() {
            psize = size;
            random = new Random(System.currentTimeMillis());
            seq = new int[size];
            for (int i=0; i<size; i++)
                seq[i] = i;
        }

        @Override
        public boolean hasNext() {
            return psize > 0;
        }

        @Override
        public K next() {
            int index = random.nextInt(psize);
            int target = seq[index];
            seq[index] = seq[--psize];
            seq[psize] = -1;
            return items[target];
        }
    }

    public static void main(String[] args) {
        // 测试API的有效性
//        RandomQueue<String> queue = new RandomQueue<>();
//        System.out.println("isEmpty = " + queue.isEmpty());
//        System.out.println("size = " + queue.size());
//        queue.enqueue("aaa");
//        queue.enqueue("bbb");
//        queue.enqueue("ccc");
//        queue.enqueue("ddd");
//        queue.enqueue("eee");
//        queue.enqueue("fff");
//        System.out.println("isEmpty = " + queue.isEmpty());
//        System.out.println("size = " + queue.size());
//
//        System.out.println("sample = " + queue.sample());
//        System.out.println("size = " + queue.size());
//        System.out.println("sample = " + queue.sample());
//        System.out.println("size = " + queue.size());
//        System.out.println("sample = " + queue.sample());
//        System.out.println("size = " + queue.size());
//
//        System.out.println("dequeue = " + queue.dequeue());
//        System.out.println("size = " + queue.size());
//        System.out.println("dequeue = " + queue.dequeue());
//        System.out.println("size = " + queue.size());
//        System.out.println("dequeue = " + queue.dequeue());
//        System.out.println("size = " + queue.size());


        // 斗地主发牌
        RandomQueue<Poker> pokerShuffle = new RandomQueue<>();
        for (String value : Poker.PokerBox)
            pokerShuffle.enqueue(new Poker(value));

        System.out.println("all poker in PokerBox:");
        Iterator<Poker> iterator = pokerShuffle.iterator();
        while (iterator.hasNext())
            System.out.print(iterator.next().getValue() + ", ");
        System.out.println();
        System.out.println("---------");

        List<Poker> pm0 = new ArrayList<>();
        List<Poker> pm1 = new ArrayList<>();
        List<Poker> pm2 = new ArrayList<>();
        Map<String, List<Poker>> player = new HashMap<>(3);
        player.put("0", pm0);
        player.put("1", pm1);
        player.put("2", pm2);

        while (pokerShuffle.size > 3) {
            pm0.add(pokerShuffle.dequeue());
            pm1.add(pokerShuffle.dequeue());
            pm2.add(pokerShuffle.dequeue());
        }

        Random random = new Random();
        String strIndex = random.nextInt(3) + "";
        player.get(strIndex).add(pokerShuffle.dequeue());
        player.get(strIndex).add(pokerShuffle.dequeue());
        player.get(strIndex).add(pokerShuffle.dequeue());

        System.out.println("poker box isEmpty = " + pokerShuffle.isEmpty());

        player.forEach((key, value) -> {
            if (key.equals(strIndex)) {
                System.out.println("地主的牌：");
            } else {
                System.out.println("贫民的牌：");
            }

            value.forEach(poker -> System.out.print(poker.getValue() + ", "));
            System.out.println();
        });
    }
}
