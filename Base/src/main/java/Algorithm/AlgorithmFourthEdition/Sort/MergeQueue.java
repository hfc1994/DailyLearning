package Algorithm.AlgorithmFourthEdition.Sort;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Queue;

/**
 * Created by user-hfc on 2020/3/22.
 */
public class MergeQueue {

    /**
     *  2.2.14 归并有序的队列。编写一个静态方法，将两个有序的队列作为参数，
     *  返回一个归并后的有序队列。
     */
    public static Queue<Integer> merge(Queue<Integer> left, Queue<Integer> right) {
        Queue<Integer> target = new Queue<>();
        
        Integer lv = left.peek();
        Integer rv = right.peek();
        
        while (lv != null || rv != null) {
            if (lv == null) target.put(right.take());
            else if (rv == null) target.put(left.take());
            else if (lv > rv) target.put(right.take());
            else target.put(left.take());

            lv = left.peek();
            rv = right.peek();
        }
        
        return target;
    }

    /**
     * 2.2.15 自底向上的有序队列归并排序。用下面的方法编写一个自底向上的归并排序：给定
     * N个元素，创建N个队列，每个队列包含其中一个元素。创建一个由这N个队列组成的队列，
     * 然后不断用练习2.2.14中的方法将队列的头两个元素归并，并将结果重新加入到队列结尾，
     * 直到队列的队列只剩下一个元素为止。
     */
    public static Queue<Queue<Integer>> mergeQueues(Queue<Queue<Integer>> queues) {
        while (queues.size() != 1) {
            int length = queues.size();
            for (int i=0; i<length;i=i+2) {
                if (i == length - 1) {
                    queues.put(queues.take());
                    break;
                }

                queues.put(MergeQueue.merge(queues.take(), queues.take()));
            }
        }

        return queues;
    }

    public static void main(String[] args) {

        // 2.2.14
//        Queue<Integer> left = new Queue<>();
//        Queue<Integer> right = new Queue<>();
//        left.put(2);
//        left.put(7);
//        left.put(18);
//        left.put(29);
//        left.put(50);
//        right.put(6);
//        right.put(16);
//        right.put(21);
//        right.put(25);
//        right.put(29);
//
//        Queue<Integer> ret = MergeQueue.merge(left, right);
//        while (ret.peek() != null) {
//            System.out.print(ret.take());
//            System.out.print(" ");
//        }
//        System.out.println();

        // 2.2.15
        int[] demo = new int[] {43,21,3,6,87,45,65,90,34,23,8,15,94,26,72,5,21,17,63,33};
        Queue<Queue<Integer>> queues = new Queue<>();
        Queue<Integer> child;
        for (int num : demo) {
            child = new Queue<>();
            child.put(num);
            queues.put(child);
        }

        Queue<Queue<Integer>> ret = MergeQueue.mergeQueues(queues);
        Queue<Integer> nums = ret.take();
        while (nums.peek() != null) {
            System.out.print(nums.take());
            System.out.print(" ");
        }
        System.out.println();
    }
}
