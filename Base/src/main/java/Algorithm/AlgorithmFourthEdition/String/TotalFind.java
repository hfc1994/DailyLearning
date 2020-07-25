package Algorithm.AlgorithmFourthEdition.String;

/**
 * Created by user-hfc on 2020/7/23.
 *
 * 暴力子字符串查找
 */
public class TotalFind {

    /**
     * 暴力子字符串查找
     */
    public static int search1(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N-M; i++) {
            int j;
            for (j=0; j<M; j++) {
                if (pat.charAt(j) != txt.charAt(i+j))
                  break;
            }
            if (j == M) return i;   //找到匹配
        }
        return -1;  // 未找到匹配
    }

    /**
     * 暴力子字符串匹配算法的显式回退实现
     */
    public static int search2(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        int i,j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            if (txt.charAt(i) == pat.charAt(j))
                j++;
            else {
                i -= j;
                j = 0;
            }
        }
        if (j == M)
            return i - M;   // 找到匹配
        else
            return -1;      // 未找到匹配
    }

    public static void main(String[] args) {
        String txt = "ABACADABRAC";
        String pat = "ABRA";
        System.out.println(search1(pat, txt));

        System.out.println(search2(pat, txt));
    }
}
