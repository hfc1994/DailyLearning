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
public class RandomGrid extends AbstractUF {

    // 网格横向和纵向的点数
    private int X;
    private int Y;

    private int UP = 1;
    private int DOWN = 1 << 1;
    private int LEFT = 1 << 2;
    private int RIGHT = 1 << 3;

    public RandomGrid(int X) {
        this(X, X);
    }

    public RandomGrid(int X, int Y) {
        this.X = X;
        this.Y = Y;
        int total = X * Y;
        id = new int[total];
        // 所有点之间两两互联的边数
        count = 2 * total - X - Y;
        // 每个点的分量标识符表示其指向的位置
        // 0x0000表示没有指向；
        // 0x0001表示指向上方；0x0010表示指向下方
        // 0x0100表示指向左方；0x1000表示指向右方
        for (int i=0; i<total; i++)
            id[i] = 0;
    }

    @Override
    public void union(int p, int q) {
        // union就是把两个点相连
        // 判断是否已经相连应该交给connection()

        int diff = p - q;
        int vector = 0;
        if (diff == -X) vector = UP;            // p在q的上边
        else if (diff == X) vector = DOWN;      // p在q的下边
        else if (diff == -1) vector = LEFT;     // p在q的左边
        else if (diff == 1) vector = RIGHT;     // p在q的右边

        if (vector != 0) {
            id[q] = id[q] | vector;
            count--;
        }
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public boolean connection(int p, int q) {
        int pV = id[p]; // p点的指向
        int qV = id[q]; // q点的指向

        // 两个点都没有指向
        if (pV == 0 && qV == 0)
            return false;

        int diff = p - q;
        if (diff == -X) return ((pV & DOWN) != 0) || ((qV & UP) != 0);          // p在q的上边
        else if (diff == X) return ((pV & UP) != 0) || ((qV & DOWN) != 0);      // p在q的下边
        else if (diff == -1) return ((pV & RIGHT) != 0) || ((qV & LEFT) != 0);  // p在q的左边
        else if (diff == 1) return ((pV & LEFT) != 0) || ((qV & RIGHT) != 0);   // p在q的右边

        // 说明是两个不直接相邻的点
        return false;
    }

    public static Connection[] generate(int M) {
        return generate(M, M);
    }

    public static Connection[] generate(int M, int N) {
        // 一个点x只会与x-1、x+1、x-M或x+M相连
        int[] vectors = new int[]{-1, 1, 0 - M, M};
        Random random = new Random();

        List<Connection> connList = new LinkedList<>();
        RandomGrid rg = new RandomGrid(M, N);

        int vIndex, vValue;
        // 第一个随机点的坐标
        int x, y;
        // 两个随机点
        int p, q;
        while (rg.count() != 0) {
            vIndex = random.nextInt(vectors.length);
            vValue = vectors[vIndex];
            vectors[vIndex] = vectors[1];
            vectors[1] = vValue;

            x = random.nextInt(M);
            y = random.nextInt(N);

            // 四种边界情况
            if (vValue == 1 && x == M-1)
                continue;
            if (vValue == -1 && x == 0)
                continue;
            if (vValue == (0-M) && y == 0)
                continue;
            if (vValue == M && y == N-1)
                continue;

            p = x + M * y;
            q = p + vValue;

            if (!rg.connection(p, q)) {
                rg.union(p, q);
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

    /**
     * 1.5.19 动画。编写一个RandomGrid（请见练习1.5.18）的用例，
     * 和我们的开发用例一样使用UnionFind来检查触点的连通性并在
     * 处理的同事用StdDraw将它们绘出。
     */
    public static void animation() {

    }

    public static void main(String[] args) {
        int N = 4;

//        Connection[] conns = generate(N);
        Connection[] conns = generate(3, 4);

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
