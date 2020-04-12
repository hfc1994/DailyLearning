package Algorithm.AlgorithmFourthEdition.PriorityQueue;

/**
 * Created by user-hfc on 2020/4/12.
 *
 * 2.4.25 计算数论。编写程序CubeSum.java，在不适用额外空间的条件下，按
 * 大小顺序打印所有a*a*a+b*b*b的结果，其中a和b为0到N之间的整数。也就是
 * 说，不要全部计算N*N个的和然后排序，而是创建一个最小优先队列，初始状态
 * 为(0*0*0, 0, 0), (1*1*1, 1, 0), ..., (N*N*N, N, 0)。这样只要优先
 * 队列非空，删除并打印最小的元素(i*i*i+j*j*j, i, j)，插入(i*i*i+(j+1)*(j+1)*(j+1), i, j+1).
 * 用这段程序找出N在0到1000之间所有满足a*a*a + b*b*b = c*c*c + d*d*d的不同整数a,b,c,d
 */
public class CubeSum implements Comparable<CubeSum> {

    private int sum;
    private int i;
    private int j;

    public CubeSum(int i, int j) {
        this.sum = i*i*i + j*j*j;
        this.i = i;
        this.j = j;
    }

    @Override
    public int compareTo(CubeSum c) {
        return this.sum - c.sum;
    }

    public static void main(String[] args) {
        int N = 10;

        MinPQ<CubeSum> minPQ = new MinPQ<>(N+1);
        for (int i=0; i<=N; i++)
            minPQ.insert(new CubeSum(i, 0));

        CubeSum deleted;
        int count = 0;
        while (!minPQ.isEmpty()) {
            deleted = minPQ.delMin();
            System.out.print(deleted.sum + " ");
            if (deleted.j < N)
                minPQ.insert(new CubeSum(deleted.i, deleted.j + 1));
            count++;
            if (count == 10) {
                System.out.println();
                count = 0;
            }
        }

        System.out.println();

        // ---------------------

        N = 1000;
        System.out.println("-----" + N + "以内满足a*a*a + b*b*b = c*c*c + d*d*d的不同整数a,b,c,d-----");

        minPQ = new MinPQ<>(N+1);
        for (int i=0; i<=N; i++)
            minPQ.insert(new CubeSum(i, 0));

        CubeSum tempDelete = null;
        String tempOutput = null;
        while (!minPQ.isEmpty()) {
            deleted = minPQ.delMin();

            if (tempDelete == null) {
                tempDelete = deleted;
            } else if (tempDelete.sum == deleted.sum) {
                if (tempOutput == null)
                    tempOutput = tempDelete.sum + " = (" + tempDelete.i + ", " + tempDelete.j + ")";
                tempOutput += ", (" + deleted.i + ", " + deleted.j + ")";
            } else {
                if (tempOutput != null)
                    System.out.println(tempOutput);
                tempDelete = null;
                tempOutput = null;
            }

            if (deleted.j < N)
                minPQ.insert(new CubeSum(deleted.i, deleted.j + 1));
        }
    }

}
