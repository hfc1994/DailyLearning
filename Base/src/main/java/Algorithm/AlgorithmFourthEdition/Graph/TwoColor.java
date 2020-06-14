package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 使用深度优先遍历搜索的二分图判读
 */
public class TwoColor {

    private boolean[] marked;
    private boolean[] color;
    private boolean isTwoColor;

    public TwoColor(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        for (int s=0; s<G.V(); s++) {
            if (!marked[s])
                dfs(G, s);
        }
    }

    public void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(G, w);
            } else if (color[w] == color[v])
                isTwoColor = true;
        }
    }

    public boolean isBipartite() {
        return isTwoColor;
    }
}
