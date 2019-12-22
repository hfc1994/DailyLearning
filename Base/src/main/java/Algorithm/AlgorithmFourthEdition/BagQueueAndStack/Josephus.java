package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

import java.util.Iterator;

/**
 * Created by hfc on 2019/12/22.
 *
 * 1.3.37 Josephus问题。在这个古老的问题中，N个身陷绝境的人一致同意通过以下
 * 方式减少生存人数。他们围坐成一圈（位置记为0到N-1）并从第一个人开始报数，报
 * 到M的人会被杀死，直到最后一个人留下来。传说中Josephus找到了不被杀死的位置。
 * 编写一个Queue的用例Josephus，从命令行接受N和M并打印出人们被杀死的顺序（这
 * 也将显示Josephus在圈中的位置）。
 * 例：java Josephus 7 2
 * 1 3 5 0 4 2 6
 */
public class Josephus implements Iterator<Integer> {

    private int[] values;
    private int size;
    private int step;
    private int current;

    public Josephus(int initCapacity, int initStep) {
        values = new int[initCapacity];
        for (int i=0; i<initCapacity; i++) {
            values[i] = i;
        }
        step = initStep;
        size = initCapacity;
        current = -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }


    @Override
    public boolean hasNext() {
        return size != 0;
    }

    @Override
    public Integer next() {
        if (!hasNext())
            return -1;

        int target = (current + step) % values.length;
        while (values[target] == -1) {
            target = (current + 1) % values.length;
        }

        int ret = values[target];
        values[target] = -1;
        current = target;
        size--;
        return ret;
    }

    public static void main(String[] args) {
        Josephus josephus = new Josephus(7, 2);
        while (josephus.hasNext())
            System.out.print(josephus.next() + " ");
        System.out.println();
    }
}
