package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/6/9.
 *
 * 能够完成点乘的稀疏向量
 */
public class SparseVector {

    private LinearProbingHashST<Integer, Double> st;

    public SparseVector() {
        st = new LinearProbingHashST<>();
    }

    public int size() {
        return st.size();
    }

    public void put(int i, double x) {
        st.put(i, x);
    }

    public double get(int i) {
        if (!st.contains(i))
            return 0.0;
        else
            return st.get(i);
    }

    public double dot(double[] that) {
        double sum = 0.0;
        for (int i : st.keys())
            sum += that[i]*this.get(i);
        return sum;
    }
}
