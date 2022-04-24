package Algorithm.Leetcode.pkg200;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hfc on 2022/4/18.
 *
 * 386. 字典序排数
 *
 * 给你一个整数 n ，按字典序返回范围 [1, n] 内所有整数。
 * 你必须设计一个时间复杂度为 O(n) 且使用 O(1) 额外空间的算法。
 *
 * 示例 1：
 * 输入：n = 13
 * 输出：[1,10,11,12,13,2,3,4,5,6,7,8,9]
 *
 * 示例 2：
 * 输入：n = 2
 * 输出：[1,2]
 *  
 * 提示：
 * 1 <= n <= 5 * 10^4
 *
 */
public class Problem386 {

    /**
     * 速度 100%
     * 内存 82%
     */
    public List<Integer> lexicalOrder1(int n) {
        List<Integer> results = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (i <= n) {
                results.add(i);
                this.findSub(results, i, n);
            } else {
                break;
            }
        }

        return results;
    }

    private void findSub(List<Integer> result, int num, int n) {
        num = num * 10;
        for (int i = 0; i <= 9; i++) {
            int newNum = num + i;
            if (newNum <= n) {
                result.add(newNum);
                this.findSub(result, newNum, n);
            } else {
                break;
            }
        }
    }

    /**
     * 题解给的灵感
     * 使用的迭代法，空间占用为 O(1)
     */
    public List<Integer> lexicalOrder(int n) {
        List<Integer> results = new ArrayList<>();
        int num = 1;
        for (int i = 1; i <= n; i++) {
            results.add(num);
            if (num * 10 <= n) {
                num *= 10;
            } else {
                while (num % 10 == 9 || num + 1 > n) {
                    num /= 10;
                }
                num++;
            }
        }

        return results;
    }

    public static void main(String[] args) {
        Problem386 p = new Problem386();

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9),
                p.lexicalOrder(13)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1, 2), p.lexicalOrder(2)));
        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1), p.lexicalOrder(1)));
        LeetcodeUtil.printList(p.lexicalOrder(27));
    }

}
