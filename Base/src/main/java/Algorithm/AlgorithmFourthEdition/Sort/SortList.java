package Algorithm.AlgorithmFourthEdition.Sort;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Node;

/**
 * Created by user-hfc on 2020/3/22.
 *
 * 2.2.17 链表排序。实现对链表的自然排序（这是将链表排序的最佳
 * 方法，因为它不需要额外的空间，且运行时间是线性对数级别）
 *
 * 想法：自然排序应该只是要其按照ASCII里的顺序来排列，比如0到9，a到z，
 * 并不一定需要像2.2.16那样处理
 */
public class SortList {

    public Node<Integer> sort(Node<Integer> head) {
        Node<Integer> oldHead = head;
        head = null;
        Node<Integer> current = null;
        Node<Integer> min;
        Node<Integer> index;
        while (oldHead != null) {
            index = oldHead;
            min = index;
            while (index != null) {
                if (index.value < min.value) {
                    min = index;
                }
                index = index.next;
            }

            if (oldHead == min)
                oldHead = oldHead.next;

            if (min.prev != null)
                min.prev.next = min.next;
            if (min.next != null)
                min.next.prev = min.prev;

            min.prev = null;
            min.next = null;
            if (head == null) {
                head = min;
                current = min;
            } else {
                min.prev = current;
                current.next = min;
                current = min;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        int[] demo = new int[] {43,21,3,6,87,45,65,90,34,23,8,15,94,26,72,5,21,17,63,33};

        Node<Integer> head = null;
        Node<Integer> current = null;
        Node<Integer> node;
        for (int num : demo) {
            node = new Node<>(num);
            if (head == null) {
                head = node;
                current = node;
            } else {
                current.next = node;
                node.prev = current;
                current = node;
            }
        }

        SortList sl = new SortList();
        head = sl.sort(head);
        current = head;
        while (current != null) {
            System.out.print(current.value + " ");
            current = current.next;
        }
        System.out.println();
    }
}
