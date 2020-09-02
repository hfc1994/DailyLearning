package Algorithm.Leetcode;

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

        // -1，0，1的特殊情况
        if (a >= -1 && a <= 1)
            return a;

        // 计算对于a的最大指数baseSub，不至于a^baseSub导致结果溢出
        int[] baseSub = {1};
        int[] baseInc = {1};
        int newA = 1;
        while (true) {
            newA *= a;
            // TODO: 2020/9/2 b超长时还是会运行时间很长，这里是否考虑换成Long的最大值
            if (Integer.MAX_VALUE / newA > a) {
                baseSub = arrayPlus(baseSub, baseInc);
            } else
                break;
        }

        if (b.length == 1 && bigNumberCompare(baseSub, b))
            return (int) Math.pow(a, b[0]);

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
        System.out.println(p.superPow(a, b));   // -1

        a = 1;
        b = new int[]{1, 0, 2, 4, 1, 0, 2, 4, 4};
        System.out.println(p.superPow(a, b));   // 1

    }
}
