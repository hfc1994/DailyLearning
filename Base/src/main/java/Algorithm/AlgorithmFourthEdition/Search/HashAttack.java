package Algorithm.AlgorithmFourthEdition.Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by user-hfc on 2020/6/7.
 *
 * 3.4.32 散列攻击。找出2^N个hashCode()方法返回值均相同且长度
 * 均为2^N的字符串。根据String类型的hashCode()方法的实现如下：
 *
 * public int hashCode() {
 *     int hash = 0;
 *     for (int i=0; i<length(); i++)
 *         hash = (hash * 31) + charAt(i);
 *     return hash;
 * }
 *
 */
public class HashAttack {

    /**
     * a-z 97-122
     * A-Z 65-90
     * 122-97+1 + 90-65+1 = 52
     */
    private static int[] alphabet = new int[52];
    static {
        int index = 0;
        for (int i=97; i<=122; i++)
            alphabet[index++] = i;

        for (int i=65; i<=90; i++)
            alphabet[index++] = i;
    }

    /**
     * 根据给定的字符串base来寻找countLimit个和其hashCode以及长度均相等的字符串
     *
     * 时间复杂度不会算，但是相对于findSameHashCodeString的循环，通过求余计算的判断可以跳过较多的循环
     *
     * @param base 给定的字符串
     * @param countLimit 限制寻找的个数，当等于-1时表示寻找全部
     * @return hashCode相等的长度相同的字符串
     */
    public List<String> buildSameHashCodeString(StringBuilder base, int countLimit) {
        List<String> ret = buildByMod(hashCode(base.toString()), countLimit, base.length());

        // 如果对个数有要求，那么只返回符合要求的情况
        if (ret.size() == countLimit || countLimit == -1)
            return ret;
        else
            return Collections.emptyList();
    }

    /**
     * 通过求余计算来判断目标hashCode能否被指定字母的ascii值整除
     * 以此来跳过部分字母
     *
     * @param hashCode 目标hashCode值
     * @param countLimit 限制寻找的个数，当等于-1时表示寻找全部
     * @param levelLimit 限制递归层级，因此也就能获得等长的字符串了
     * @return hashCode相等的长度相同的字符串
     */
    private List<String> buildByMod(int hashCode, int countLimit, int levelLimit) {
        List<String> retList = new ArrayList<>();
        if (levelLimit == 0)
            return retList;

        int temp;
        for (int letter : alphabet) {
            temp = hashCode - letter;
            if (temp % 31 == 0) {
                temp = temp / 31;
                if ((temp >= 97 && temp <= 122) || (temp >= 65 && temp <= 90)) {
                    retList.add(String.valueOf((char) temp) + String.valueOf((char) letter));
                    countLimit--;
                } else {
                    List<String> tmpRetList = buildByMod(temp, countLimit, --levelLimit);
                    if (tmpRetList.size() != 0) {
                        tmpRetList.forEach(tmpRet -> retList.add(tmpRet + String.valueOf((char) letter)));
                        countLimit = countLimit - tmpRetList.size();
                    }
                }
            }
            if (countLimit == 0)
                break;
        }
        return retList;
    }

    /**
     * 根据给定的字符串base来寻找countLimit个和其hashCode以及长度均相等的字符串
     *
     * 时间复杂度应该是52^N，与base的长度相关
     *
     * @param base 给定的字符串
     * @param countLimit 限制寻找的个数，当等于-1时表示寻找全部
     * @return hashCode相等的长度相同的字符串
     */
    public List<String> findSameHashCodeString(StringBuilder base, int countLimit) {
        String strBase = base.toString();
        int hashCode = hashCode(strBase);

        int length =base.length();
        base.setLength(0);
        for (int i=0; i<length; i++)
            base.append("a");

        List<String> retList = new ArrayList<>();
        if (hashCode(base.toString()) == hashCode) {
            retList.add(base.toString());
            countLimit--;
        }
        // 从base开始递增
        while (countLimit != 0 && increaseCharAt(base, base.length() - 1)) {
            strBase = base.toString();
            if (hashCode(strBase) == hashCode) {
                retList.add(strBase);
                countLimit--;
            }
        }

        // 如果对个数有要求，那么只返回符合要求的情况
        // 初始countLimit=-1时，结果会是<0
        if (countLimit <= 0)
            return retList;
        else
            return Collections.emptyList();
    }

    /**
     * 对index位置的char进行递增1
     * 从a到z再到A最后到Z
     * a-z 97-122
     * A-Z 65-90
     * 从字符串尾部向头部的顺序来倒序处理
     *
     * @param sb 递增的字符串对象
     * @param index 对index位的字母进行递增
     * @return 递增是否完成，true：递增完成；false：递增失败
     *         当sb已经达到其最大的值时会失败，比如2位长度最大值是ZZ
     */
    protected boolean increaseCharAt(StringBuilder sb, int index) {
        int iChar = (int) sb.charAt(index);
        if (iChar == 90) { // 是Z
            if (index != 0 && increaseCharAt(sb, index-1)) {
                sb.setCharAt(index, (char) 97); // Z循环递增到a
            } else
                return false;
        } else if (iChar == 122) {   // 是z
            sb.setCharAt(index, (char) 65); // z递增到A
        } else {
            sb.setCharAt(index, (char) (iChar + 1));
        }
        return true;
    }

    private int hashCode(String str) {
        int hash = 0;
        for (int i=0; i<str.length(); i++)
            hash = (hash * 31) + str.charAt(i);
        return hash;
    }

    public static void main(String[] args) {
        int N = 1;
        int countLimit = -1;    // -1表示寻找全部

        // 准备基础数据
        int count = Double.valueOf(Math.pow(2, N)).intValue();
        StringBuilder baseSb = new StringBuilder();
        StringBuilder tmpBaseSb;
        for (int i=0; i<count; i++)
            baseSb.append("a");
        long begin; // 计时

        List<String> retList;
        HashAttack ha = new HashAttack();
        int total = 0;  // 找到的组数

        System.out.println("----------------------");
        retList = ha.findSameHashCodeString(new StringBuilder(baseSb), count);
        if (retList.size() > 1) {
            retList.forEach(ret -> System.out.print(ret + " "));
            System.out.println();
        }

        /**
         * 循环法
         */
        System.out.println("----------------------");
        total = 0;
        tmpBaseSb = new StringBuilder(baseSb);
        begin = System.currentTimeMillis();
        while (ha.increaseCharAt(tmpBaseSb, tmpBaseSb.length() -1)) {
            retList = ha.findSameHashCodeString(new StringBuilder(tmpBaseSb), countLimit);
            if (retList.size() > 1) {
                total += 1;
                retList.forEach(ret -> System.out.print(ret + " "));
                System.out.println();
            }
        }
        System.out.println("符合条件的数据有：" + total + " 组");
        System.out.println("总计耗时：" + (System.currentTimeMillis() - begin) + " ms");

        /**
         * 求余递归法
         */
        System.out.println("----------------------");
        total = 0;
        tmpBaseSb = new StringBuilder(baseSb);
        begin = System.currentTimeMillis();
        while (ha.increaseCharAt(tmpBaseSb, tmpBaseSb.length() -1)) {
            retList = ha.buildSameHashCodeString(new StringBuilder(tmpBaseSb), countLimit);
            if (retList.size() > 1) {
                total += 1;
                retList.forEach(ret -> System.out.print(ret + " "));
                System.out.println();
            }
        }
        System.out.println("符合条件的数据有：" + total + " 组");
        System.out.println("总计耗时：" + (System.currentTimeMillis() - begin) + " ms");
    }
}
