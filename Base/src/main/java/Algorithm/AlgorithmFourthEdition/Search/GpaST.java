package Algorithm.AlgorithmFourthEdition.Search;

/**
 * Created by user-hfc on 2020/5/7.
 *
 * 3.1.1 编写一段程序，创建一张符号表并建立字母成绩和数值分数的
 * 对应关系，如下表所示。从标准输入读取一系列字母成绩，
 * 计算并打印GPA（字母成绩对应的分数的平均值）
 *
 * A+    A     A-     B+    B      B-     C+     C      C-     D      F
 * 4.33  4.00  3.67   3.33  3.00   2.67   2.33   2.00   1.67   1.00   0.00
 *
 */
public class GpaST<k, v> extends SequentialSearchST<k, v> {

    public static void main(String[] args) {
        String[] alphabet = new String[]{"A+", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D", "F"};
        Float[] mark = new Float[] {4.33f, 4.00f, 3.67f, 3.33f, 3.00f, 2.67f, 2.33f, 2.00f, 1.67f, 1.00f, 0.00f};

        GpaST<String, Float> gpaST = new GpaST<>();
        for (int i=0; i<alphabet.length; i++) {
            gpaST.put(alphabet[i], mark[i]);
        }

        gpaST.keys().forEach(key -> System.out.println(key + " = " + gpaST.get(key)));

        float totalMark = 0.0f;
        int totalAlphabet = 0;
        String[] actualAlphabet = new String[]{"C-","B+","A-","A","D","A+","A","C","D","B+","B","C+"};
        for (int i=0; i<actualAlphabet.length; i++) {
            totalMark += gpaST.get(actualAlphabet[i]);
            totalAlphabet++;
        }
        System.out.println("avg mark is " + totalMark / totalAlphabet);
    }
}
