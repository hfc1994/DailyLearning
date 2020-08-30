package Algorithm.Leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hfc on 2020/8/28.
 *
 * 17. 电话号码的字母组合
 *
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 示例:
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 * 说明:
 * 尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。
 *
 */
public class Problem17 {

    private char[][] letterMap = new char[10][4];
    {
        letterMap[2] = new char[]{'a', 'b', 'c'};
        letterMap[3] = new char[]{'d', 'e', 'f'};
        letterMap[4] = new char[]{'g', 'h', 'i'};
        letterMap[5] = new char[]{'j', 'k', 'l'};
        letterMap[6] = new char[]{'m', 'n', 'o'};
        letterMap[7] = new char[]{'p', 'q', 'r', 's'};
        letterMap[8] = new char[]{'t', 'u', 'v'};
        letterMap[9] = new char[]{'w', 'x', 'y', 'z'};
    }

    public List<String> letterCombinations1(String digits) {
        List<String> ret = new ArrayList<>();
        if (digits.length() > 0)
            doBuild(0, digits, "", ret, letterMap);

        return ret;
    }

    public List<String> letterCombinations2(String digits) {
        Queue queue = new Queue();
        String prefix;
        char[] cs;
        int index, size;
        for (char c : digits.toCharArray()) {
            index = Integer.parseInt(String.valueOf(c));
            cs = letterMap[index];
            size = queue.size();
            if (size == 0) {
                for (char tmp : cs)
                    queue.put(String.valueOf(tmp));
            } else {
                for (int i=0; i<size; i++) {
                    prefix = queue.take();
                    for (char tmp : cs)
                        queue.put(prefix + String.valueOf(tmp));
                }
            }
        }

        int tmpSize = queue.size;
        List<String> ret = new ArrayList<>(tmpSize);
        for (int i=0; i<tmpSize; i++)
            ret.add(queue.take());

        return ret;
    }

    private void doBuild(int index, String digits, String prefix, List<String> ret, char[][] letterMap) {
        int lIndex = Integer.parseInt(String.valueOf(digits.charAt(index)));
        char[] cs = letterMap[lIndex];
        if (index < digits.length() - 1) {
            index++;
            for (char c : cs)
                doBuild(index, digits, prefix + String.valueOf(c), ret, letterMap);
        } else {
            for (char c : cs)
                ret.add(prefix + String.valueOf(c));
        }
    }

    class Node {
        public String value;
        public Node prev;
        public Node next;
        public Node() {}
        public Node(String value) {
            this.value = value;
        }
        public Node(String value, Node next) {
            this.value = value;
            this.next = next;
            next.prev = this;
        }
    }

    class Queue {
        private Node head;
        private Node tail;
        private int size;

        public Queue() {}

        public int size() {
            return size;
        }

        public void put(String value) {
            Node newNode = new Node(value);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            size++;
        }

        public String take() {
            if (size == 0)
                return null;

            Node temp = head;
            if (head == tail) {
                head = null;
                tail = head;
            } else {
                head = head.next;
                temp.next.prev = null;
                temp.next = null;
            }

            size--;
            return temp.value;
        }
    }

    public static void main(String[] args) {
        Problem17 p = new Problem17();

        System.out.print("[");
        p.letterCombinations1("23").forEach(l1 -> System.out.print(l1 + ","));
        System.out.println("]");
        System.out.println();

        System.out.print("[");
        p.letterCombinations2("23").forEach(l2 -> System.out.print(l2 + ","));
        System.out.println("]");
        System.out.println();
    }
}
