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

    /**
     * 1.3.47 可连接的队列、栈或steque。为队列、栈或steque（请见联系1.3.32）添加一个能够（破坏性地）
     * 连接两个同类对象的额外操作catenation
     */
    public void catenation(Steque<K> s) {
        Node<K> origin = this.first;
        this.first = null;
        this.size = 0;

        if (null != origin) {
            do {
                this.push(origin.value);
            } while ((origin = origin.next) != null);
        }

        if (s != null) {
            while (s.peek() != null)
                this.push(s.pop());
        }
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


        System.out.println("\n----1.3.47-----\n");
        Steque<String> steque2 = new Steque<>();
        steque2.push("2aa");
        steque2.push("2bb");
        steque2.push("2cc");

        steque.catenation(steque2);

        for (String aSteque1 : steque)
            System.out.print(aSteque1 + " ");
        System.out.println();
    }
}
