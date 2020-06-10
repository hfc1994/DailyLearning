package Algorithm.AlgorithmFourthEdition.Search;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by user-hfc on 2020/6/10.
 *
 * 3.5.20 对照索引。编写一个ST的用例Concordance，为从标准输入得到的字符串
 * 构建对照索引并打印出来。
 * 对照索引会给出每个单词在书中出现的所有位置。
 */
public class Concordance {

    private int total;  // 键值对总数
    private int size;   // 散列表大小（数组长度）
    private Node[] table;

    public Concordance() {
        this(16);
    }

    public Concordance(int size) {
        this.size = size;
        this.table = new Node[size];
    }

    public void put(String key, CordIndex ci) {
        int hash = hash(key);

        Node head = table[hash];
        Node x = head;
        while (x != null) {
            if (x.key.equals(key)) {
                x.vals.add(ci); // ci可能出现重复
                return;
            } else
                x = x.next;
        }

        table[hash] = new Node(key, ci, head);
    }

    public List<CordIndex> get(String key) {
        int hash = hash(key);

        Node x = table[hash];
        while (x != null) {
            if (x.key.equals(key))
                return x.vals;
            else
                x = x.next;
        }

        return null;
    }

    public void delete(String key) {
        int hash = hash(key);

        Node x = table[hash];
        Node prev = null;
        while (x != null) {
            if (x.key.equals(key)) {
                if (prev == null)
                    table[hash] = x.next;
                else {
                    prev.next = x.next;
                    x.next = null;
                }
            } else {
                prev = x;
                x = x.next;
            }
        }
    }

    public int size() {
        return total;
    }

    // TODO: 2020/6/10 输入一个单词创建一个索引效率较低，可以考虑初始直接做全部单词的索引
    public void buildIndex(File f, String targetWord) {
        String line;
        int linePadding = 0;
        int wordIndex = 0;

        try (FileReader fr = new FileReader(f);
             BufferedReader br = new BufferedReader(fr)) {

            while ((line = br.readLine()) != null) {
                // 行数
                linePadding++;
                if (!line.contains(targetWord))
                    continue;

                while (wordIndex < line.length()) {
                    wordIndex = line.indexOf(targetWord, wordIndex);

                    if (wordIndex != -1)
                        put(targetWord, new CordIndex(linePadding, wordIndex + 1));  // 从0开始变为从1开始
                    else
                        break;

                    // 把指针后移
                    wordIndex += targetWord.length();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % size;
    }

    private static class CordIndex {
        int line_padding;   // 第几行
        int word_padding;   // 第几个单词

        public CordIndex(int linePadding, int wordPadding) {
            line_padding = linePadding;
            word_padding = wordPadding;
        }
    }

    private static class Node {
        String key;
        List<CordIndex> vals;
        Node next;

        public Node(String key, CordIndex val, Node next) {
            this.key = key;
            this.vals = new ArrayList<>();
            this.vals.add(val);
            this.next = next;
        }
    }

    public static void main(String[] args) {

        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tale.txt";
        File f = new File(path);
        Concordance cd = new Concordance();

        Scanner input = new Scanner(System.in);
        String word;
        System.out.println("---请输入---");
        while ((word = input.nextLine()) != null) {
            word = word.trim();
            if (!"".equals(word)) {
                System.out.println("开始创建索引...");
                cd.buildIndex(f, word);
                System.out.println("结束索引创建...");
                List<CordIndex> ciList = cd.get(word);
                if (ciList != null)
                    ciList.forEach(ci -> System.out.println(ci.line_padding + " 行，" + ci.word_padding + " 列") );
            }
        }
    }
}
