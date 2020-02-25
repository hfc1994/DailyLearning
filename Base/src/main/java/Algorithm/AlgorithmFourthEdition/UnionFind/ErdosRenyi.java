package Algorithm.AlgorithmFourthEdition.UnionFind;

import java.util.Random;

/**
 * Created by user-hfc on 2020/2/24.
 *
 * 1.5.17 随机连接。设计UF的一个用例ErdosRenyi，从命令行接受一个整数N，
 * 在0到N-1之间产生随机整数对，调用connected()判断它们是否相连，如果
 * 不是则调用union()方法（和我们的开发用例一样）。不断循环直到所有触点
 * 均相互连通并打印出生成的连接总数。将你的程序打包成一个接受参数N并
 * 返回连接总数的静态方法count()，添加一个main()方法从命令行接受N，
 * 调用count()并打印它的返回值。
 *
 * 想法：看题意的话，这个连接总数总是等于N-1啊？这题有什么意义？
 */
public class ErdosRenyi {

    public static int count(int N) {
        Random r = new Random();
        HeightWeightQuickUnion hwqu = new HeightWeightQuickUnion(N);
        int edges = 0;

        while (hwqu.count() > 1) {
            int p = r.nextInt(N);
            int q = r.nextInt(N);
            if (!hwqu.connection(p, q)) {
                hwqu.union(p, q);
                edges++;
            }
        }

        return edges;
    }

    public static void main(String[] args) {
        int N = 1000;

        int edges = count(N);

        System.out.println(edges);
    }
}
