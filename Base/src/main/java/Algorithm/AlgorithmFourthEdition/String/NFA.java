package Algorithm.AlgorithmFourthEdition.String;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Bag;
import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.Stack;
import Algorithm.AlgorithmFourthEdition.Graph.Digraph;
import Algorithm.AlgorithmFourthEdition.Graph.DirectedDFS;

/**
 * Created by user-hfc on 2020/7/26.
 *
 * 正则表达式的模式匹配
 * 使用了非确定性有限状态自动机
 */
public class NFA {

    private char[] re;      // 匹配转换
    private Digraph G;      // epsilon转换
    private int M;          // 状态数量

    public NFA(String regexp) {
        // 根据给定的正则表达式构造NFA
        Stack<Integer> ops = new Stack<>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);

        for (int i=0; i<M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|')
                ops.push(i);
            else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or+1);
                    G.addEdge(or, i);
                } else {
                    lp = or;
                }
            }

            if (i < M - 1 && re[i+1] == '*') {  // 查看下一个字符
                G.addEdge(lp, i+1);
                G.addEdge(i+1, lp);
            }

            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i+1);
        }
    }

    public boolean recognizes(String txt) {
        // NFA是否能够识别文本txt
        Bag<Integer> pc = new Bag<>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v=0; v<G.V(); v++) {
            if (dfs.marked(v))
                pc.add(v);
        }

        for (int i=0; i<txt.length(); i++) {
            // 计算txt[i+1]可能到达的所有NFA状态
            Bag<Integer> match = new Bag<>();
            for (int v : pc) {
                if (v < M && (re[v] == txt.charAt(i) || re[v] == '.'))
                    match.add(v+1);
            }
            pc = new Bag<>();
            dfs = new DirectedDFS(G, match);
            for (int v=0; v<G.V(); v++) {
                if (dfs.marked(v))
                    pc.add(v);
            }
        }

        for (int v : pc) {
            if (v == M)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String regex = "((A*B|AC)D)";
        String txt = "ABD";

        NFA nfa = new NFA(regex);
        System.out.println(nfa.recognizes(txt));
    }

}
