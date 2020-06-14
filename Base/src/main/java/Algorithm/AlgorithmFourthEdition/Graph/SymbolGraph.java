package Algorithm.AlgorithmFourthEdition.Graph;

import Algorithm.AlgorithmFourthEdition.Search.SequentialSearchST;
import edu.princeton.cs.algs4.In;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 用字符串来替代数字索引的图
 */
public class SymbolGraph implements Symbol {

    private SequentialSearchST<String, Integer> st; // 符号名 -> 索引
    private String[] keys;  // 索引 -> 符号名
    private Graph G;    // 图

    public SymbolGraph(String stream, String sp) {
        st = new SequentialSearchST<>();
        In in = new In(stream);
        while (in.hasNextLine()) {  // 构造索引
            String[] a = in.readLine().split(sp);   // 读取字符串

            for (int i=0; i<a.length; i++) {    // 为每个不同的字符串关联一个索引
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
            }
        }
        keys = new String[st.size()];   // 用来获得顶点的反向索引是一个数组

        for (String name : st.keys())
            keys[st.get(name)] = name;

        G = new Graph(st.size());
        in = new In(stream);
        while (in.hasNextLine()) {  // 构造图
            String[] a = in.readLine().split(sp);   // 将每一行的第一个顶点和该行的其它顶点相连
            int v = st.get(a[0]);
            for (int i=1; i<a.length; i++)
                G.addEdge(v, st.get(a[i]));
        }
    }

    @Override
    public boolean contains(String key) {
        return st.contains(key);
    }

    @Override
    public int index(String key) {
        return st.get(key);
    }

    @Override
    public String name(int v) {
        return keys[v];
    }

    @Override
    public Graph G() {
        return G;
    }
}
