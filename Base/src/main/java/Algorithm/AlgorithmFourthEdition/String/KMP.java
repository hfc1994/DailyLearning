package Algorithm.AlgorithmFourthEdition.String;

/**
 * Created by user-hfc on 2020/7/23.
 *
 * Knuth-Morris-Pratt字符串查找算法
 * 模式匹配
 */
public class KMP {

    private String pat;
    private int[][] dfa;

    public KMP(String pat) {
        // 由模式字符串构造DFA
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            // 计算dfa[][j]
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];      // 赋值匹配失败情况下的值

            dfa[pat.charAt(j)][j] = j+1;    // 设置匹配成功情况下的值

            X = dfa[pat.charAt(j)][X];      // 更新重启状态
        }
    }

    public int search(String txt) {
        // 在txt上模拟DFA的运行
        int N = txt.length();
        int M = this.pat.length();
        int i, j;
        for (i = 0,j = 0; i < N && j < M; i++)
            j = dfa[txt.charAt(i)][j];
        if (j == M)
            return i - M;   // 找到匹配（到达模式字符串的结尾 ）
        else
            return -1;       // 未找到匹配
    }

    public static void main(String[] args) {
        String txt = "FINDINAHAYSTACKNEEDLEINA";
        String pat = "NEEDLE";

        KMP kmp = new KMP(pat);
        System.out.println(kmp.search(txt));
    }
}
