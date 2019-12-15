package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/15.
 */
public class Node<K> {

    public K item;

    public Node<K> prev;

    public Node<K> next;

    public Node() {}

    public Node(K item) {
        this.item = item;
    }

    public Node(K item, Node<K> next) {
        this.item = item;
        this.next = next;
        next.prev = this;
    }
}
