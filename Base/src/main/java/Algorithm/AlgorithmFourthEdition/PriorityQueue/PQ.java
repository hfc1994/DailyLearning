package Algorithm.AlgorithmFourthEdition.PriorityQueue;

/**
 * Created by user-hfc on 2020/4/6.
 */
public interface PQ<T> {

    boolean isEmpty();

    int size();

    void insert(T v);

    T del();
}
