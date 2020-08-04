package Algorithm.AlgorithmFourthEdition.String;

import Algorithm.AlgorithmFourthEdition.PriorityQueue.MinPQ;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by user-hfc on 2020/8/2.
 *
 * 霍夫曼编码算法
 */
public class Huffman {

    private static int R = 256;     // ASCII字母表

    // 霍夫曼单词查找树中的节点
    private static class Node implements Comparable<Node> {

        private char ch;    // 字符，内部结点不会使用该变量
        private int freq;   // 字符出现的频率，展开过程不会使用该变量
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(Node that) {
            return this.freq = that.freq;
        }
    }

    // 通过前缀码字典查找树构造编译表
    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private static void buildCode(String[] st, Node x, String s) {
        if (x.isLeaf()) {
            st[x.ch] = s;
            return;
        }
        buildCode(st, x.left, s + '0');
        buildCode(st, x.right, s + '1');
    }

    // 使用多棵单结点树初始化优先队列
    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char c=0; c<R; c++) {
            if (freq[c] > 0)
                pq.insert(new Node(c, freq[c], null, null));
        }

        while (pq.size() > 1) {
            // 合并两棵频率最小的树
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    // 输出单词查找树的比特字符串
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    // 从比特流的前序表示中重建单词查找树
    private static Node readTrie() {
        if (BinaryStdIn.readBoolean())
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        return new Node('\0', 0, readTrie(), readTrie());
    }

    // 压缩
    public static void compress() {
        compress(null);
    }

    public static void compress(String src) {
        // 读取输入
        String s = src == null ? BinaryStdIn.readString() : src;
        char[] input = s.toCharArray();
        // 统计频率
        int[] freq = new int[R];
        for (int i=0; i<input.length; i++)
            freq[input[i]]++;

        // 构造霍夫曼树
        Node root = buildTrie(freq);

        // （递归地）构造编译表
        String[] st = buildCode(root);

        // （递归地）打印解码用的单词查找树
        writeTrie(root);

        // 打印字符总数
        BinaryStdOut.write(input.length);

        //使用霍夫曼编码处理输入
        for (int i=0; i<input.length; i++) {
            String code = st[input[i]];
            for (int j=0; j<code.length(); j++) {
                if (code.charAt(j) == '1')
                    BinaryStdOut.write(true);
                else
                    BinaryStdOut.write(false);
            }
        }
        BinaryStdOut.close();
    }

    // 前缀码的展开（解码）
    public static void expand() {
        Node root = readTrie();
        // 获取字符总数
        int N = BinaryStdIn.readInt();
        for (int i=0; i<N; i++) {
            // 展开第i个编码所对应的字母
            Node x = root;
            while (!x.isLeaf()) {
                if (BinaryStdIn.readBoolean())
                    x = x.right;
                else
                    x = x.left;
            }
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) throws IOException {
        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\abra.txt";
        String content = new String(Files.readAllBytes(Paths.get(path)));

        ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
        PrintStream cacheStream = new PrintStream(baoStream);
        PrintStream oldStream = System.out;
        System.setOut(cacheStream);

        Huffman.compress(content);

        byte[] bytes = baoStream.toByteArray();
        System.setOut(oldStream);

        // TODO: 2020/8/2 增加IO测试工具，需要可以读写位数据
    }


}
