package Algorithm.AlgorithmFourthEdition.Graph;

import edu.princeton.cs.algs4.In;

import java.io.File;

/**
 * Created by user-hfc on 2020/7/5.
 * todo -- 没理解
 * 优先级限制下的并行任务调度问题的关键路径方法
 */
public class CPM {

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\jobsPC.txt";
        File f = new File(path);
        In in= new In(f);

        int N = in.readInt();
        in.readLine();
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2*N + 2);
        int s = 2*N;    // 起始点
        int t = 2*N + 1;    // 结束点
        for (int i=0; i<N; i++) {
            String[] a = in.readLine().split("\\s+");
            double duration = Double.parseDouble(a[0]);
            G.addEdge(new DirectedEdge(i, i+N, duration));
            G.addEdge(new DirectedEdge(s, i, 0.0));

            G.addEdge(new DirectedEdge(i+N, t, 0.0));
            for (int j=1; j<a.length; j++) {
                int successor = Integer.parseInt(a[j]);
                G.addEdge(new DirectedEdge(i + N, successor, 0.0));
            }
        }
        AcyclicLP lp = new AcyclicLP(G, s);

        System.out.println("Start times: ");
        for (int i=0; i<N; i++)
            System.out.printf("%4d: %5.1f\n", i, lp.distTo(i));

        System.out.printf("Finish time: %5.1f\n", lp.distTo(t));
    }
}
