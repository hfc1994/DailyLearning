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
public class Concordance<k, v> {

    private int total;  // 键值对总数
    private int size;   // 散列表大小（数组长度）
    private Node<k, v>[] table;

    public Concordance() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public Concordance(int size) {
        this.size = size;
        this.table = new Node[size];
    }

    public void put(k key, v val) {
        int hash = hash(key);

        Node<k, v> head = table[hash];
        Node<k, v> x = head;
        while (x != null) {
            if (x.key.equals(key)) {
                x.vals.add(val); // val可能出现重复
                return;
            } else
                x = x.next;
        }

        table[hash] = new Node<>(key, val, head);
    }

    public List<v> get(k key) {
        int hash = hash(key);

        Node<k, v> x = table[hash];
        while (x != null) {
            if (x.key.equals(key))
                return x.vals;
            else
                x = x.next;
        }

        return null;
    }

    public void delete(k key) {
        int hash = hash(key);

        Node<k, v> x = table[hash];
        Node<k, v> prev = null;
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

    private int hash(k key) {
        return (key.hashCode() & 0x7fffffff) % size;
    }

    private class Node<KEY, VALUE> {
        KEY key;
        List<VALUE> vals;
        Node<KEY, VALUE> next;

        public Node(KEY key, VALUE val, Node<KEY, VALUE> next) {
            this.key = key;
            this.vals = new ArrayList<>(8);
            this.vals.add(val);
            this.next = next;
        }
    }

    public static void main(String[] args) {

        String path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tale.txt";
        path = System.getProperty("user.dir") + "\\resources\\algs4-data\\tinyTale.txt";
        File f = new File(path);

        // 按输入的关键字建索引
//        Concordance<String, CordIndex> cd = new Concordance<>();
//        Scanner input = new Scanner(System.in);
//        String word;
//        System.out.println("---请输入关键字---");
//        while ((word = input.nextLine()) != null) {
//            word = word.trim();
//            if (!"".equals(word)) {
//                System.out.println("开始创建索引...");
//                buildIndex(f, word, cd);
//                System.out.println("结束索引创建...");
//                List<CordIndex> ciList = cd.get(word);
//                if (ciList != null)
//                    ciList.forEach(ci -> System.out.println(ci.line_padding + " 行，" + ci.word_padding + " 列") );
//            }
//        }

        // 全量建索引
//        Concordance<String, CordIndex> cd = buildFullIndex(f);
//        if (cd != null) {
//            Scanner input = new Scanner(System.in);
//            String word;
//            System.out.println("---请输入关键字---");
//            while ((word = input.nextLine()) != null) {
//                word = word.trim();
//                if (!"".equals(word)) {
//                    List<CordIndex> ciList = cd.get(word);
//                    if (ciList != null)
//                        ciList.forEach(ci -> System.out.println(ci.line_padding + " 行，" + ci.word_padding + " 列") );
//                    else
//                        System.out.println("没有。。。");
//                }
//            }
//        }

        // 全量反向索引
        Concordance<CordIndex, String> cd = buildFullInvertedIndex(f);
        if (cd != null) {
            Scanner input = new Scanner(System.in);
            String word;
            System.out.println("---请输入坐标---");
            CordIndex ci;
            String[] words;
            while ((word = input.nextLine()) != null) {
                word = word.trim();
                if (!"".equals(word)) {
                    words = word.split(" ");
                    ci = new CordIndex(Integer.parseInt(words[0]), Integer.parseInt(words[1]));
                    List<String> strs = cd.get(ci);
                    if (strs != null)
                        System.out.println(strs.get(0));
                    else
                        System.out.println("没有。。。");
                }
            }
        }
    }

    // 按关键字建索引
    public static void buildIndex(File f, String targetWord, Concordance<String, CordIndex> cd) {
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
                        cd.put(targetWord, new CordIndex(linePadding, wordIndex + 1));  // 从0开始变为从1开始
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

    // 全量建索引
    public static Concordance<String, CordIndex> buildFullIndex(File f) {
        String line;
        int linePadding = 0;
        Concordance<String, CordIndex> cd = new Concordance<>();

        try (FileReader fr = new FileReader(f);
             BufferedReader br = new BufferedReader(fr)) {

            while ((line = br.readLine()) != null) {
                // 行数
                linePadding++;

                int beginIndex = 0;
                for (int i=0; i<line.length(); i++) {
                    if (isBlank(line.charAt(i))) {
                        cd.put(line.substring(beginIndex, i), new CordIndex(linePadding, beginIndex + 1));
                        beginIndex = i+1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cd;
    }

    /**
     * 3.5.21 反向对照索引。编写一个程序InvertedConcordance，从标准输入接受一个对照索引并在标准
     * 输出中打印出原始的字符串。
     *
     * 实际上不应该用Concordance做测试的，因为此时Node.vals有且仅有一个元素。。。
     * 但是懒了，知道大概什么意思就行
     */
    // 全量反向索引
    public static Concordance<CordIndex, String> buildFullInvertedIndex(File f) {
        String line;
        int linePadding = 0;
        Concordance<CordIndex, String> cd = new Concordance<>();

        try (FileReader fr = new FileReader(f);
             BufferedReader br = new BufferedReader(fr)) {

            while ((line = br.readLine()) != null) {
                // 行数
                linePadding++;

                int beginIndex = 0;
                for (int i=0; i<line.length(); i++) {
                    if (isBlank(line.charAt(i))) {
                        cd.put(new CordIndex(linePadding, beginIndex + 1), line.substring(beginIndex, i));
                        beginIndex = i+1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return cd;
    }

    public static boolean isBlank(char c) {
        return c <= 32;
    }
}

// 行列对象
class CordIndex {
    int line_padding;   // 第几行
    int word_padding;   // 第几个单词

    public CordIndex(int linePadding, int wordPadding) {
        line_padding = linePadding;
        word_padding = wordPadding;
    }

    @Override
    public int hashCode() {
        return line_padding + word_padding * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj instanceof CordIndex) {
            CordIndex ci = (CordIndex) obj;
            return (ci.word_padding == this.word_padding
                    && ci.line_padding == this.line_padding);
        }
        return false;
    }
}
