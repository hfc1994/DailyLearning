package Algorithm.Leetcode.pkg400;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by hfc on 2022/3/23.
 *
 * 440. 字典序的第K小数字
 *
 * 给定整数 n 和 k，返回  [1, n] 中字典序第 k 小的数字。
 *
 * 示例 1:
 * 输入: n = 13, k = 2
 * 输出: 10
 * 解释: 字典序的排列是 [1, 10, 11, 12, 13, 2, 3, 4, 5, 6, 7, 8, 9]，所以第二小的数字是 10。
 *
 * 示例 2:
 * 输入: n = 1, k = 1
 * 输出: 1
 *
 * 提示:
 * 1 <= k <= n <= 10^9
 *
 */
public class Problem440 {

    /**
     * 内存超限
     */
    public int findKthNumber1(int n, int k) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++)  {
            list.add(String.valueOf(i));
        }

        list = list.stream().sorted().collect(Collectors.toList());
        return Integer.parseInt(list.get(k - 1));
    }

    /**
     * 超时
     */
    public int findKthNumber2(int n, int k) {
        AtomicInteger counter = new AtomicInteger();
        for (int i = 1; i <= 9; i++) {
            if (i <= n) {
                counter.incrementAndGet();
                if (counter.intValue() == k) return i;
                int ret = this.findSub(counter, k, i, n);
                if (counter.intValue() == k) return ret;
            } else {
                break;
            }
        }

        return -1;
    }

    private int findSub(AtomicInteger counter, int k, int num, int n) {
        num = num * 10;
        for (int i = 0; i <= 9; i++) {
            int newNum = num + i;
            if (newNum <= n) {
                counter.incrementAndGet();
                if (counter.intValue() == k) return newNum;
                int ret = this.findSub(counter, k, newNum, n);
                if (counter.intValue() == k) return ret;
            } else {
                break;
            }
        }

        return -1;
    }

    /**
     * fixme 解答错误
     */
    public int findKthNumber(int n, int k) {
        int num = 1;
        for (int i = 1; i < k; i++) {
            if (num * 10 <= n) {
                num *= 10;
            } else {
                while (num % 10 == 9 || num + 1 > n) {
                    num /= 10;
                }
                num++;
            }
        }

        return num;
    }

    public static void main(String[] args) {
        Problem440 p = new Problem440();

        System.out.println(10 == p.findKthNumber(13, 2));
        System.out.println(1 == p.findKthNumber(1, 1));
        System.out.println(1173001 == p.findKthNumber(14289384, 1922239));
        System.out.println(416126219 == p.findKthNumber(681692778, 351251360));
    }
}
