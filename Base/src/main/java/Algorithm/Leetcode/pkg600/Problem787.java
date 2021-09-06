package Algorithm.Leetcode.pkg600;


import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by hfc on 2020/10/6.
 *
 * 787. K 站中转内最便宜的航班
 *
 * 有 n 个城市通过 m 个航班连接。每个航班都从城市 u 开始，以价格 w 抵达 v。
 * 现在给定所有的城市和航班，以及出发城市 src 和目的地 dst，你的任务是找到
 * 从 src 到 dst 最多经过 k 站中转的最便宜的价格。 如果没有这样的路线，则输出 -1。
 *
 * 示例 1：
 * 输入:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * 输出: 200
 * 解释:
 * 城市航班图如下
 * [0] -----
 *  |100   |500
 *  v      v
 * [1] -> [2]
 *    100
 * 从城市 0 到城市 2 在 1 站中转以内的最便宜价格是 200，如图中红色所示。
 *
 * 示例 2：
 * 输入:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 0
 * 输出: 500
 * 解释:
 * 城市航班图如下
 * [0] -----
 *  |100   |500
 *  v      v
 * [1] -> [2]
 *    100
 * 从城市 0 到城市 2 在 0 站中转以内的最便宜价格是 500，如图中蓝色所示。
 *  
 *
 * 提示：
 * n 范围是 [1, 100]，城市标签从 0 到 n - 1.
 * 航班数量范围是 [0, n * (n - 1) / 2].
 * 每个航班的格式 (src, dst, price).
 * 每个航班的价格范围是 [1, 10000].
 * k 范围是 [0, n - 1].
 * 航班没有重复，且不存在环路
 *
 */
public class Problem787 {

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
//        return solution1(n, flights, src, dst, K);
//        return solution2(n, flights, src, dst, K);
        return solution3(n, flights, src, dst, K);
    }

    // 反向处理，比较慢
    public int solution1(int n, int[][] flights, int src, int dst, int K) {
        int[][] dp = new int[n][n];
        for (int[] flight : flights) {
            dp[flight[0]][flight[1]] = flight[2];
        }

        int hop = K, curDst, prevDst = -1;
        Queue<Integer> dstQueue = new LinkedList<>();
        dstQueue.add(dst);
        int count = dstQueue.size();
        while (count != 0) {
            curDst = dstQueue.remove();
            if (curDst == src) {
                if (prevDst == -1)
                    return 0;
            } else {
                for (int i=0; i<n; i++) {
                    if (dp[i][curDst] > 0) {    // 跳过空路径
                        dstQueue.add(i);
                        if (curDst != dst) {
                            int tmpPrice = dp[i][curDst] + dp[curDst][dst];
                            if (dp[i][dst] == 0 || dp[i][dst] > tmpPrice)
                                dp[i][dst] = tmpPrice;
                        }
                    }
                }
            }

            if (--count == 0) {
                if (--hop < 0) break;
                count = dstQueue.size();
                prevDst = curDst;
            }
        }

        return dp[src][dst] == 0 ? -1 : dp[src][dst];
    }

    private int n;
    private int src;
    private int dst;
    private int minPrice;
    public int solution2(int n, int[][] flights, int src, int dst, int K) {
        int[][] dp = new int[n][n];
        for (int[] flight : flights) {
            dp[flight[0]][flight[1]] = flight[2];
        }

        this.minPrice = 0;
        this.n = n;
        this.src = src;
        this.dst = dst;
        doDfs(dp, K, src);

        return minPrice == 0 ? -1 : minPrice;
    }

    private void doDfs(int[][] dp, int hop, int curSrc) {
        if (curSrc == dst) {
            if (minPrice == 0 || dp[src][dst] < minPrice)
                minPrice = dp[src][dst];
            return;
        }
        if (hop < 0) return;

        int temp;
        for (int i=0; i<n; i++) {
            if (i == curSrc) continue;
            if (dp[curSrc][i] > 0) {    // 去除空路径
                temp = dp[src][i];
                dp[src][i] = curSrc == src ? dp[src][i] : dp[src][curSrc] + dp[curSrc][i];

                doDfs(dp, hop-1, i);

                dp[src][i] = temp;
            }
        }
    }

    public int solution3(int n, int[][] flights, int src, int dst, int K) {
        int[][] dp = new int[n][n];
        for (int[] flight : flights) {
            dp[flight[0]][flight[1]] = flight[2];
        }

        int hop = K, curSrc;
        Queue<Integer> srcQueue = new LinkedList<>();
        srcQueue.add(src);
        int count = srcQueue.size();
        while (count != 0) {
            curSrc = srcQueue.remove();
            if (curSrc == dst) {
                if (curSrc == src)
                    return 0;
            } else {
                for (int i=0; i<n; i++) {
                    if (hop == 0 && i != dst) continue;
                    if (dp[curSrc][i] > 0) {
                        if (curSrc != src) {
                            int tmpPrice = dp[src][curSrc] + dp[curSrc][i];
                            if (dp[src][i] == 0 || dp[src][i] > tmpPrice) {
                                dp[src][i] = dp[src][curSrc] + dp[curSrc][i];
                                srcQueue.add(i);
                            }
                        } else
                            srcQueue.add(i);
                    }
                }
            }

            if (--count == 0) {
                if (--hop < 0) break;
                count = srcQueue.size();
            }
        }

        return dp[src][dst] == 0 ? -1 : dp[src][dst];
    }

    public static void main(String[] args) {
        Problem787 p = new Problem787();

        int n=3, src=0, dst=2, k=1;
        int[][] edges = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 200

        n=3;
        src=0;
        dst=2;
        k=0;
        edges = new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 500

        n=4;
        src=0;
        dst=3;
        k=2;
        edges = new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}, {1, 3, 700}, {2, 3, 300}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 500

        n=4;
        src=0;
        dst=3;
        k=2;
        edges = new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}, {1, 3, 300}, {2, 3, 300}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 400

        n=4;
        src=0;
        dst=3;
        k=0;
        edges = new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}, {1, 3, 700}, {2, 3, 300}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // -1

        n=4;
        src=0;
        dst=0;
        k=0;
        edges = new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}, {1, 3, 700}, {2, 3, 300}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 0

        n=4;
        src=0;
        dst=1;
        k=2;
        edges = new int[][]{{1, 0, 100}, {1, 2, 100}, {0, 2, 500}, {1, 3, 700}, {2, 3, 300}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // -1

        n=15;
        src=1;
        dst=4;
        k=10;
        edges = new int[][]{{10,14,43},{1,12,62},{4,2,62},{14,10,49},{9,5,29},{13,7,53},{4,12,90},{14,9,38},
                {11,2,64},{2,13,92},{11,5,42},{10,1,89},{14,0,32},{9,4,81},{3,6,97},{7,13,35},{11,9,63},{5,7,82},
                {13,6,57},{4,5,100},{2,9,34},{11,13,1},{14,8,1},{12,10,42},{2,4,41},{0,6,55},{5,12,1},{13,3,67},
                {3,13,36},{3,12,73},{7,5,72},{5,6,100},{7,6,52},{4,7,43},{6,3,67},{3,1,66},{8,12,30},{8,3,42},
                {9,3,57},{12,6,31},{2,7,10},{14,4,91},{2,3,29},{8,9,29},{2,11,65},{3,8,49},{6,14,22},{4,6,38},
                {13,0,78},{1,10,97},{8,14,40},{7,9,3},{14,6,4},{4,8,75},{1,6,56}};
        long begin = System.currentTimeMillis();
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 169
        System.out.println(System.currentTimeMillis() - begin + "ms");

        n=4;
        src=0;
        dst=3;
        k=1;
        edges = new int[][]{{0,1,1},{0,2,5},{1,2,1},{2,3,1}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 6

        // FIXME: 2020/10/6 结果不正确
        n=5;
        src=0;
        dst=4;
        k=2;
        edges = new int[][]{{0,1,1},{0,2,5},{1,2,1},{2,3,1},{3,4,1}};
        System.out.println(p.findCheapestPrice(n, edges, src, dst, k)); // 7
    }
}
