package Algorithm.Leetcode.pkg200;

/**
 * Created by hfc on 2022/1/10.
 *
 * 306. 累加数
 *
 * 累加数 是一个字符串，组成它的数字可以形成累加序列。
 * 一个有效的 累加序列 必须 至少 包含 3 个数。除了最开始的两个数以外，字符串中的其他数都等于它之前两个数相加的和。
 * 给你一个只包含数字 '0'-'9' 的字符串，编写一个算法来判断给定输入是否是 累加数 。如果是，返回 true ；否则，返回 false 。
 * 说明：累加序列里的数 不会 以 0 开头，所以不会出现 1, 2, 03 或者 1, 02, 3 的情况。
 *
 * 示例 1：
 * 输入："112358"
 * 输出：true
 * 解释：累加序列为: 1, 1, 2, 3, 5, 8 。1 + 1 = 2, 1 + 2 = 3, 2 + 3 = 5, 3 + 5 = 8
 *
 * 示例 2：
 * 输入："199100199"
 * 输出：true
 * 解释：累加序列为: 1, 99, 100, 199。1 + 99 = 100, 99 + 100 = 199
 *
 * 提示：
 * 1 <= num.length <= 35
 * num 仅由数字（0 - 9）组成
 *
 * 进阶：你计划如何处理由过大的整数输入导致的溢出?
 *
 */
public class Problem306 {

    /**
     * 速度与内存都一般
     */
    public boolean isAdditiveNumber(String num) {
        if (num.length() <= 2) return false;

        int lIdx = 0;
        for (int lLen = 1; lLen <= num.length() / 2; lLen++) {
            long fNum = this.toNumber(num, lIdx, lLen);
            if (this.isAdditiveNumber(num, fNum, lIdx + lLen, 1)) {
                return true;
            }
        }

        return false;
    }

    public boolean isAdditiveNumber(String num, long fNum, int rIdx, int len) {
        long baseNum = fNum;
        int baseIdx = rIdx;
        int baseLen = len;
        while (baseLen <= num.length() / 2) {
            rIdx = baseIdx;

            long latterNum = this.toNumber(num, rIdx, len);
            if (latterNum != -1L) {
                while (len <= num.length() / 2) {
                    long accNum = fNum + latterNum;
                    int endIdx = findMatch(num, accNum, rIdx + len);
                    if (endIdx == -1) {
                        len++;
                        if (num.length() - rIdx - len < len) {
                            break;
                        }

                        if (rIdx + len <= num.length()) {
                            latterNum = this.toNumber(num, rIdx, len);
                            if (latterNum == -1L) break;
                        }
                        continue;
                    } else if (endIdx == num.length()) {
                        return true;
                    }

                    rIdx = rIdx + len;
                    fNum = latterNum;
                    latterNum = accNum;
                }
            }

            fNum = baseNum;
            len = ++baseLen;
        }
        return false;
    }

    public long toNumber(String num, int idx, int len) {
        if (len != 1 && num.charAt(idx) == '0') return -1L;
        return Long.parseLong(num.substring(idx, idx + len));
    }

    public int findMatch(String num, long cpNum, int fromIdx) {
        String str = String.valueOf(cpNum);
        if (str.length() > (num.length() - fromIdx)) {
            return -1;
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != num.charAt(fromIdx + i)) {
                return -1;
            }
        }

        return fromIdx + str.length();
    }

    public static void main(String[] args) {
        Problem306 p = new Problem306();

        System.out.println(!p.isAdditiveNumber("11"));
        System.out.println(p.isAdditiveNumber("000"));
        System.out.println(p.isAdditiveNumber("0000"));
        System.out.println(!p.isAdditiveNumber("1203"));
        System.out.println(!p.isAdditiveNumber("000011"));
        System.out.println(p.isAdditiveNumber("011"));
        System.out.println(p.isAdditiveNumber("101"));
        System.out.println(p.isAdditiveNumber("112"));
        System.out.println(!p.isAdditiveNumber("1023"));
        System.out.println(p.isAdditiveNumber("10010"));
        System.out.println(p.isAdditiveNumber("112358"));
        System.out.println(p.isAdditiveNumber("98199"));
        System.out.println(p.isAdditiveNumber("199100199"));
        System.out.println(!p.isAdditiveNumber("199100198"));
        System.out.println(p.isAdditiveNumber("19919921983"));
        System.out.println(p.isAdditiveNumber("199111992"));
        System.out.println(p.isAdditiveNumber("111122335588143"));
    }
}
