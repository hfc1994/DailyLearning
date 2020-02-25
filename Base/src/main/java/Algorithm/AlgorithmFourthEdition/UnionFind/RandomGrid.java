package Algorithm.AlgorithmFourthEdition.UnionFind;

import Algorithm.AlgorithmFourthEdition.BagQueueAndStack.RandomBag;

import java.util.*;

/**
 * Created by user-hfc on 2020/2/25.
 *
 * 1.5.18 随机网格生成器。编写一个程序RandomGrid，从命令行接受一个int值N，
 * 生成一个N*N的网格中的所有连接。它们的排列随机且方向随机（即(p q)和(q p)
 * 出现的可能性是相等的），将这个结果打印到标准输出中。可以使用RandomBag将
 * 所有连接随机排列（请见练习1.3.34），并使用如下所示是Connection嵌套类
 * 来将p和q封装到一个对象中。将程序打包成两个静态方法：generate()，接受参
 * 数N并返回一个连接的数组；main()，从命令行接受参数N，调用generate()，
 * 遍历返回的数组并打印出所有连接。
 *
 * private class Connection {
 *     int p;
 *     int q;
 *
 *     public Connection(int p, int q) {
 *         this.p = p;
 *         this.q = q;
 *     }
 * }
 *
 * 想法：在一个N*N的网格中，随机给定一个点x，那么这个点只会与x-1、x+1、x-N或x+N相连
 */
public class RandomGrid {

    public static Connection[] generate(int N) {
        // 一个点x只会与x-1、x+1、x-N或x+N相连
        int[] links = new int[]{-1, 1, 0 - N, N};
        Random random = new Random();

        List<Connection> connList = new LinkedList<>();
        QuickUnion qu = new QuickUnion(N * N);

        int linkIndex, linkValue;
        // 第一个随机点的坐标
        int x, y;
        // 两个随机点
        int p, q;
        while (qu.count() > 1) {
            linkIndex = random.nextInt(4);
            linkValue = links[linkIndex];
            links[linkIndex] = links[1];
            links[1] = linkValue;

            x = random.nextInt(N);
            y = random.nextInt(N);

            // 四种边界情况
            if (linkValue == 1 && x == N-1)
                continue;
            if (linkValue == -1 && x == 0)
                continue;
            if (linkValue == (0-N) && y == 0)
                continue;
            if (linkValue == N && y == N-1)
                continue;

            p = x + N * y;
            q = p + linkValue;

            // FIXME: 2020/2/26 这个只是一个最小的网格连线，即点与邻近点可达但没有直接相连
            if (!qu.connection(p, q)) {
                qu.union(p, q);
                connList.add(new Connection(p, q));
            }
        }

        return connList.toArray(new Connection[0]);
    }

    private static class Connection {
        int p;
        int q;

        public Connection(int p, int q) {
            this.p = p;
            this.q = q;
        }
    }

    public static void main(String[] args) {
        int N = 3;

        Connection[] conns = generate(N);

        RandomBag<Connection> rb = new RandomBag<>(conns.length);
        for (Connection conn : conns)
            rb.add(conn);

        Iterator<Connection> iterator = rb.iterator();
        int i = 0;
        Connection conn;
        while (iterator.hasNext()) {
            conn = iterator.next();
            System.out.printf("第%4d组 %4d --- %4d\n", ++i, conn.p, conn.q);
        }
    }
}
