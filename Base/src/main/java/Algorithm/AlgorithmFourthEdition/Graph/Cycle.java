package Algorithm.AlgorithmFourthEdition.Graph;

/**
 * Created by user-hfc on 2020/6/14.
 *
 * 使用深度优先搜索来判断图G是否是无环图
 * 假设不存在自环和平行边
 */
public class Cycle {

    private boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        for (int s=0; s<G.V(); s++) {
            if (!marked[s])
                dfs(G, s, s);
        }
    }

    public void dfs(Graph G, int v, int u) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w])
                dfs(G, w, v);
            else if (w != u) {
                /**
                 * v是u的相邻点，也就是在查找u的相邻点时获取的v（u->v）
                 * w是v的相邻点（v->w）
                 * 在无环状态下，深度优先遍历在往深处遍历时，
                 * 只有当v的相邻点w其实就是u时，这时才会出现marked[w] == true
                 * 那么排除w == u的情况，那么就说出现了“环”
                 */
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}
