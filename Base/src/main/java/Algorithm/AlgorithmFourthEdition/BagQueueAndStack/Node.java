package Algorithm.AlgorithmFourthEdition.BagQueueAndStack;

/**
 * Created by hfc on 2019/12/15.
 */
public class Node<K> {

    public K value;

    public Node<K> prev;

    public Node<K> next;

    public Node() {}

    public Node(K value) {
        this.value = value;
    }

    public Node(K value, Node<K> next) {
        this.value = value;
        this.next = next;
        next.prev = this;
    }
}
