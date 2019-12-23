package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by user-hfc on 2019/12/16.
 *
 * 1.3.32 Steque，一个以栈为目标的队列（或称steque），是一种支持push、pop和enqueue操作
 * 的数据类型。为这种抽象数据类型定义一份API并给出一份基于链表的实现。
 */
public class Steque<K> extends Stack<K> {

    public void enqueue(K value) {

        Node<K> tail = first;
        while (tail.next != null) {
            tail = tail.next;
        }

        Node<K> newNode = new Node<>();
        newNode.value = value;
        tail.next = newNode;
    }

    public static void main(String[] args) {
        Steque<String> steque = new Steque<>();
        steque.push("aaa");
        steque.push("bbb");
        steque.push("ccc");

        for (String aSteque : steque)
            System.out.print(aSteque + " ");
        System.out.println();

        steque.enqueue("ooo");

        for (String aSteque1 : steque)
            System.out.print(aSteque1 + " ");
        System.out.println();
    }
}
