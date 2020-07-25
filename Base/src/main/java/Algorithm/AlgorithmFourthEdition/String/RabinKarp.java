package Algorithm.AlgorithmFourthEdition.String;

/**
 * Created by user-hfc on 2020/7/23.
 *
 * Rabin-Karp指纹字符串查找算法
 */
public class RabinKarp {

    private long patHash;       // 模式字符串的散列值
    private int M;              // 模式字符串的长度
    private long Q;             // 一个很大的素数
    private int R = 256;        // 字母表的大小
    private long RM;            // R^(M-1)%Q

    public RabinKarp(String pat) {
        this.M = pat.length();
        Q = 997;    // longRandomPrime()
        RM = 1;
        for (int i = 1; i <= M-1; i++)  // 计算R^(M-1)%Q
            RM = (R * RM) % Q;          // 用于减去第一个数字时的计算
        patHash = hash(pat, M);
    }

    private long hash(String key, int M) {
        // 计算key[0...M-1]的散列值
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }

    private int search(String txt) {
        // 在文本中查找相等数列值
        int N = txt.length();
        long txtHash = hash(txt, M);
        /*
         * 根据原书的意思，在hash不大于Q之前，任意两个不同字符串的hash结果是不一样的
         * 问题1：Q是否会太小
         *       Q会选一个尽可能大的值。这里通常会应用蒙特卡洛算法，即Q会取一个大于10^20的long值，
         *       如果还嫌不够大（冲突概率不够小），那么就应用两次蒙特卡洛算法（相当于10^40）。
         * 问题2：为什么不大于Q之前，任意任意两个不同字符串的hash结果是不一样的
         *       可以看到hash算法里的R在这里使用的是256，也就是ASCII码的数量。当此时我换成数字来举例，
         *       那么此处的R=10，字符“256”的hash结果是256，很快我们就能反应过来，不同字符串的hash结果
         *       确实就是不同的
         */
        if (patHash == txtHash)
            return 0;   // 一开始就匹配成功
        for (int i = M; i < N; i++) {
            // 减去第一个数字，加上最后一个数字，再次检查匹配
            txtHash = (txtHash + Q - RM*txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash*R + txt.charAt(i)) % Q;
            if (patHash == txtHash)
                return i - M + 1;       // 找到匹配
        }
        return -1;      // 未找到匹配
    }

    public static void main(String[] args) {
        String txt = "3141592653589793";
        String pat = "26535";

        RabinKarp rk = new RabinKarp(pat);
        System.out.println(rk.search(txt));
    }
}
