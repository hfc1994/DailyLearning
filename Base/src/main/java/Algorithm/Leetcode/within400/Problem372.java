package Algorithm.Leetcode.within400;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by hfc on 2020/8/30.
 *
 * 372. 超级次方
 *
 * 你的任务是计算 a^b 对 1337 取模，a 是一个正整数，b 是一个非常大的正整数且会以数组形式给出。
 *
 * 示例 1:
 * 输入: a = 2, b = [3]
 * 输出: 8
 *
 * 示例 2:
 * 输入: a = 2, b = [1,0]
 * 输出: 1024
 *
 */
public class Problem372 {

    private int baseMode = 1337;

    public int superPow(int a, int[] b) {
//        return solution1(a, b);
        return solution2(a, b);
    }

    private int solution1(int a, int[] b) {
        // -1，0，1的特殊情况
        if (a == 0 || a == 1)
            return a;
        if (a == -1)
            return (int) Math.pow(a, b[b.length - 1]);

        // 主要针对第一轮出现的比较大的a值
        a = a % baseMode;

        // 计算对于a的最大指数baseSub，不至于a^baseSub导致结果溢出
        int[] baseSub = {1};
        int[] baseInc = {1};
        int newA = 1;
        while (true) {
            newA *= a;
            if (Integer.MAX_VALUE / newA > a) {
                baseSub = arrayPlus(baseSub, baseInc);
            } else
                break;
        }

        if (b.length == 1 && bigNumberCompare(baseSub, b))
            return (int) Math.pow(a, b[0]);

        // 使用减法递减速度太慢，可以改成使用除法，不过大数除法实现略麻烦
        // 对指数进行递减任务
        int[] newB = new int[] {0}; // 递减循环次数，即b/baseSub的值
        while (bigNumberCompare(b, baseSub)) {
            b = arraySub(b, baseSub);   // 最后的b相当于时余数，即b%baseSub的值
            newB = arrayPlus(newB, baseInc);
        }

        int s1 = superPow(newA % baseMode, newB);
        int left = 0;
        for (int i=0; i<b.length; i++)
            left = left * 10 + b[i];
        int s2 = ((int) Math.pow(a, left) % baseMode);
        return (s1 * s2) % baseMode;
    }

    /**
     * 依据类似 2^1024 = (2^102)^10 * 2^4
     *         2^102 = (2^10)^10 * 2*2
     */
    private int solution2(int a, int[] b) {
        Deque<Integer> deque = new LinkedList<>();
        for (int i : b)
            deque.add(i);
        return mySuperPow(a, deque);
    }

    /**
     * 计算a^b的余数值，其中b是数值链表
     */
    private int mySuperPow(int a, Deque<Integer> b) {
        if (b.size() != 1)
            return (mySuperPow(a, b.removeLast()) * mySuperPow(mySuperPow(a, b), 10)) % baseMode;
        return mySuperPow(a, b.removeLast());
    }

    /**
     * 计算a^b的余数值，其中b是具体数值
     */
    private int mySuperPow(int a, int b) {
        if (a == baseMode)
            return 0;

        // 处理过大的a
        a = a % baseMode;
        int ret = 1;
        for (int i=b; i>0; i--)
            ret = (a * ret) % baseMode; // 每次都求余，避免溢出

        return ret;
    }

    /**
     * 对数组型大数的加法
     * @param base 被加数
     * @param plus 加数
     * @return 加法结果
     */
    private int[] arrayPlus(int[] base, int[] plus) {
        int baseLen = base.length;
        int plusLen = plus.length;
        int inc;
        for (int i=plusLen-1; i>=0; i--) {
            inc = plus[i];
            for (int j=baseLen-plusLen+i; j>=0 && inc>0; j--) {
                base[j] += inc;
                if (base[j] >= 10) {
                    base[j] -= 10;
                    inc = 1;    // 高位需要+1了
                } else
                    inc = 0;
            }

            // 循环结束时，inc不为0表示base长度需要加长了，即进位了
            if (inc != 0) {
                int[] newBase = new int[base.length + 1];
                newBase[0] = 1;
                System.arraycopy(base, 0, newBase, 1, base.length);
                base = newBase;
                baseLen = base.length;
            }
        }

        return base;
    }

    /**
     * 对数组型大数的减法
     * 注意：不支持结果为负数的情况，即调用前需要通过bigNumberCompare(base, sub)来
     *      判断base是否大于等于sub，成立才能调用该方法
     * @param base 被减数
     * @param sub 减数
     * @return 减法的结果
     */
    private int[] arraySub(int[] base, int[] sub) {
        int baseLen = base.length;
        int subLen = sub.length;
        int dec;
        for (int i=subLen-1; i>=0; i--) {
            dec = sub[i];
            for (int j=baseLen-subLen+i; j>=0 && dec>0; j--) {
                base[j] -= dec;
                if (base[j] < 0) {
                    base[j] += 10;
                    dec = 1;    // 高位需要减1
                } else
                    dec = 0;
            }

            // 提前判断过大小，不存在这个情况
            // if (dec != 0) {}
        }

        // 返回前去除多余的0前缀
        return clearZeroPrefix(base);
    }

    /**
     * 判断数组型大数的大小
     * 当base大于等于sub的时候返回true
     * base和sub都不能有0前缀，即不能是类似[0, 1, 2]的大数
     * @return base大于等于sub时返回true，否则返回false
     */
    private boolean bigNumberCompare(int[] base, int[] sub) {
        if (base.length < sub.length) return false;
        if (base.length == sub.length) {
            boolean bigger = false;
            for (int i=0; i<base.length && !bigger; i++) {
                if (base[i] < sub[i]) return false;
                else if (base[i] > sub[i]) bigger = true;
            }
        }
        return true;
    }

    private int[] clearZeroPrefix(int[] base) {
        // 计算有几个0前缀
        int count = 0;
        while (count < base.length && base[count] == 0) {
            count++;
        }

        // 去除多余0前缀
        if (count != 0) {
            if (count == base.length)
                base = new int[]{0};    // 全是0则返回一个0
            else {
                int newLen = base.length - count;
                int[] newBase = new int[newLen];
                System.arraycopy(base, count, newBase, 0, newLen);
                base = newBase;
            }
        }
        return base;
    }

    public static void main(String[] args) {
        Problem372 p = new Problem372();

        long begin = System.currentTimeMillis();
        int a = 3;
        int[] b = new int[]{1, 0, 2, 4};
        System.out.println(p.superPow(a, b));   // 1124

        a = 2;
        b = new int[]{1, 0, 2, 4};
        System.out.println(p.superPow(a, b));   // 457

        a = 8;
        b = new int[]{8, 8};
        System.out.println(p.superPow(a, b));   // 1030

        a = 188;
        b = new int[]{8, 8};
        System.out.println(p.superPow(a, b));   // 211

        a = 188;
        b = new int[]{1, 0, 2, 4};
        System.out.println(p.superPow(a, b));   // 169

        a = 999;
        b = new int[]{1, 0, 2, 4, 1, 0, 2, 4, 4};
        System.out.println(p.superPow(a, b));   // 92

        a = 0;
        b = new int[]{1, 0, 2, 4, 1, 0, 2, 4, 4};
        System.out.println(p.superPow(a, b));   // 0

        a = -1;
        b = new int[]{1, 0, 2, 4, 1, 0, 2, 4, 4};
        System.out.println(p.superPow(a, b));   // 1

        a = -1;
        b = new int[]{1, 0, 2, 4, 1, 0, 2, 4, 3};
        System.out.println(p.superPow(a, b));   // -1

        a = 1;
        b = new int[]{1, 0, 2, 4, 1, 0, 2, 4, 4};
        System.out.println(p.superPow(a, b));   // 1

        a = 2147483647;
        b = new int[]{2, 0, 0};
        System.out.println(p.superPow(a, b));   // 1198

        a = 78267;
        b = new int[]{1,7,7,4,3,1,7,0,1,4,4,9,2,8,5,0,0,9,3,1,2,5,9,6,0,9,9,0,9,6,0,5,3,7,9,8,8,
                9,8,2,5,4,1,9,3,8,0,5,9,5,6,1,1,8,9,3,7,8,5,8,5,5,3,0,4,3,1,5,4,1,7,9,6,8,8,9,8,
                0,6,7,8,3,1,1,1,0,6,8,1,1,6,6,9,1,8,5,6,9,0,0,1,7,1,7,7,2,8,5,4,4,5,2,9,6,5,0,8,
                1,0,9,5,8,7,6,0,6,1,8,7,2,9,8,1,0,7,9,4,7,6,9,2,3,1,3,9,9,6,8,0,8,9,7,7,7,3,9,5,
                5,7,4,9,8,3,0,1,2,1,5,0,8,4,4,3,8,9,3,7,5,3,9,4,4,9,3,3,2,4,8,9,3,3,8,2,8,1,3,2,
                2,8,4,2,5,0,6,3,0,9,0,5,4,1,1,8,0,4,2,5,8,2,4,2,7,5,4,7,6,9,0,8,9,6,1,4,7,7,9,7,
                8,1,4,4,3,6,4,5,2,6,0,1,1,5,3,8,0,9,8,8,0,0,6,1,6,9,6,5,8,7,4,8,9,9,2,4,7,7,9,9,
                5,2,2,6,9,7,7,9,8,5,9,8,5,5,0,3,5,8,9,5,7,3,4,6,4,6,2,3,5,2,3,1,4,5,9,3,3,6,4,1,
                3,3,2,0,0,4,4,7,2,3,3,9,8,7,8,5,5,0,8,3,4,1,4,0,9,5,5,4,4,9,7,7,4,1,8,7,5,2,4,9,
                7,9,1,7,8,9,2,4,1,1,7,6,4,3,6,5,0,2,1,4,3,9,2,0,0,2,9,8,4,5,7,3,5,8,2,3,9,5,9,1,
                8,8,9,2,3,7,0,4,1,1,8,7,0,2,7,3,4,6,1,0,3,8,5,8,9,8,4,8,3,5,1,1,4,2,5,9,0,5,3,1,
                7,4,8,9,6,7,2,3,5,5,3,9,6,9,9,5,7,3,5,2,9,9,5,5,1,0,6,3,8,0,5,5,6,5,6,4,5,1,7,0,
                6,3,9,4,4,9,1,3,4,7,7,5,8,2,0,9,2,7,3,0,9,0,7,7,7,4,1,2,5,1,3,3,6,4,8,2,5,9,5,0,
                8,2,5,6,4,8,8,8,7,3,1,8,5,0,5,2,4,8,5,1,1,0,7,9,6,5,1,2,6,6,4,7,0,9,5,6,9,3,7,8,
                8,8,6,5,8,3,8,5,4,5,8,5,7,5,7,3,2,8,7,1,7,1,8,7,3,3,6,2,9,3,3,9,3,1,5,1,5,5,8,1,
                2,7,8,9,2,5,4,5,4,2,6,1,3,6,0,6,9,6,1,0,1,4,0,4,5,5,8,2,2,6,3,4,3,4,3,8,9,7,5,5,
                9,1,8,5,9,9,1,8,7,2,1,1,8,1,5,6,8,5,8,0,2,4,4,7,8,9,5,9,8,0,5,0,3,5,5,2,6,8,3,4,
                1,4,7,1,7,2,7,5,8,8,7,2,2,3,9,2,2,7,3,2,9,0,2,3,6,9,7,2,8,0,8,1,6,5,2,3,0,2,0,0,
                0,9,2,2,2,3,6,6,0,9,1,0,0,3,5,8,3,2,0,3,5,1,4,1,6,8,7,6,0,9,8,0,1,0,4,5,6,0,2,8,
                2,5,0,2,8,5,2,3,0,2,6,7,3,0,0,2,1,9,0,1,9,9,2,0,1,6,7,7,9,9,6,1,4,8,5,5,6,7,0,6,
                1,7,3,5,9,3,9,0,5,9,2,4,8,6,6,2,2,3,9,3,5,7,4,1,6,9,8,2,6,9,0,0,8,5,7,7,0,6,0,5,
                7,4,9,6,0,7,8,4,3,9,8,8,7,4,1,5,6,0,9,4,1,9,4,9,4,1,8,6,7,8,2,5,2,3,3,4,3,3,1,6,
                4,1,6,1,5,7,8,1,9,7,6,0,8,0,1,4,4,0,1,1,8,3,8,3,8,3,9,1,6,0,7,1,3,3,4,9,3,5,2,4,
                2,0,7,3,3,8,7,7,8,8,0,9,3,1,2,2,4,3,3,3,6,1,6,9,6,2,0,1,7,5,6,2,5,3,5,0,3,2,7,2,
                3,0,3,6,1,7,8,7,0,4,0,6,7,6,6,3,9,8,5,8,3,3,0,9,6,7,1,9,2,1,3,5,1,6,3,4,3,4,1,6,
                8,4,2,5};
        System.out.println(p.superPow(a, b));   // 70

        System.out.println(System.currentTimeMillis() - begin + "ms");
    }
}
