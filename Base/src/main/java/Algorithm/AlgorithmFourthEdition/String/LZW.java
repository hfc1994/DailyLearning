package Algorithm.AlgorithmFourthEdition.String;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Created by user-hfc on 2020/8/2.
 *
 * LZW编码算法
 */
public class LZW {

    private static final int R = 256;   // 输入字符数
    private static final int L = 4096;  // 编码总数=2^12
    private static final int W = 12;    // 编码宽度

    // 压缩
    public static void compress() {
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<>();

        for (int i=0; i<R; i++)
            st.put("" + (char) i, i);
        int code = R+1;     // R为文件结束（EOF）的编码

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);   // 找到匹配的最长前缀
            BinaryStdOut.write(st.get(s), W);       // 打印出s的编码

            int t = s.length();
            if (t<input.length() && code<L)     // 将s加入符号表
                st.put(input.substring(0, t+1), code++);
            input = input.substring(t);
        }

        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    // 解码
    public static void expand() {
        String[] st = new String[L];

        int i;      // 下一个待补全的编码值
        for (i=0; i<R; i++)     // 用字符初始化编译表
            st[i] = "" + (char) i;
        st[i++] = " ";  // （未使用）文件结束标记（EOF）的前瞻字符

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];
        while (true) {
            BinaryStdOut.write(val);    // 输出当前子字符串
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R)
                break;
            String s = st[codeword];    // 获取下一个编码
            if (i == codeword)              // 如果前瞻字符不可用
                s = val + val.charAt(0);    // 根据上一个字符串的首字母得到编码的字符串
            if (i < L)
                st[i++] = val + s.charAt(0);    // 为编译表添加新的条目
            val = s;
        }
        BinaryStdOut.close();
    }
}
