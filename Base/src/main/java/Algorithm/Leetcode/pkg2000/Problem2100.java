package Algorithm.Leetcode.pkg2000;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.*;

/**
 * Created by hfc on 2022/3/6.
 *
 * 2100. 适合打劫银行的日子
 *
 * 你和一群强盗准备打劫银行。给你一个下标从 0 开始的整数数组 security ，其中 security[i] 
 * 是第 i 天执勤警卫的数量。日子从 0 开始编号。同时给你一个整数 time 。
 * 如果第 i 天满足以下所有条件，我们称它为一个适合打劫银行的日子：
 * - 第 i 天前和后都分别至少有 time 天。
 * - 第 i 天前连续 time 天警卫数目都是非递增的。
 * - 第 i 天后连续 time 天警卫数目都是非递减的。
 * 更正式的，第 i 天是一个合适打劫银行的日子当且仅当：security[i - time] >= security[i - time + 1]
 * >= ... >= security[i] <= ... <= security[i + time - 1] <= security[i + time].
 * 请你返回一个数组，包含 所有 适合打劫银行的日子（下标从 0 开始）。返回的日子可以 任意 顺序排列。
 *
 * 示例 1：
 * 输入：security = [5,3,3,3,5,6,2], time = 2
 * 输出：[2,3]
 * 解释：
 * 第 2 天，我们有 security[0] >= security[1] >= security[2] <= security[3] <= security[4] 。
 * 第 3 天，我们有 security[1] >= security[2] >= security[3] <= security[4] <= security[5] 。
 * 没有其他日子符合这个条件，所以日子 2 和 3 是适合打劫银行的日子。
 *
 * 示例 2：
 * 输入：security = [1,1,1,1,1], time = 0
 * 输出：[0,1,2,3,4]
 * 解释：
 * 因为 time 等于 0 ，所以每一天都是适合打劫银行的日子，所以返回每一天。
 *
 * 示例 3：
 * 输入：security = [1,2,3,4,5,6], time = 2
 * 输出：[]
 * 解释：
 * 没有任何一天的前 2 天警卫数目是非递增的。
 * 所以没有适合打劫银行的日子，返回空数组。
 *
 * 示例 4：
 * 输入：security = [1], time = 5
 * 输出：[]
 * 解释：
 * 没有日子前面和后面有 5 天时间。
 * 所以没有适合打劫银行的日子，返回空数组。
 *  
 * 提示：
 * 1 <= security.length <= 10^5
 * 0 <= security[i], time <= 10^5
 *
 */
public class Problem2100 {

    /**
     * 速度 22%
     * 内存 79%
     */
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        if (time > security.length / 2)
            return Collections.emptyList();

        int[] diff = new int[security.length];
        diff[0] = 0;
        for (int i = 1; i < security.length; i++) {
            diff[i] = security[i] - security[i - 1];
        }

        int inc = 0, desc = -1;
        Deque<Integer> idxStack = new ArrayDeque<>();
        for (int i = 0; i < diff.length; i++) {
            if (diff[i] <= 0) {
                // 刚从递增变递减
                while (inc > 0 && !idxStack.isEmpty()
                        && (i - idxStack.peek()) <= time) {
                    if (diff[i] == 0 && (i - idxStack.peek()) == time) {
                        break;
                    }
                    idxStack.pop();
                }

                // 持续递减的
                if (diff[i] < 0 && !idxStack.isEmpty()
                        && (i - idxStack.peek()) <= time) {
                    idxStack.pop();
                }

                desc++;
                inc = 0;
            } else {
                inc++;
                desc = 0;
            }

            if (desc >= time) {
                idxStack.push(i);
            }
        }

        List<Integer> list = new ArrayList<>(idxStack.size());
        while (!idxStack.isEmpty()) {
            int pos = idxStack.removeLast();
            if (diff.length - 1 - pos >= time) {
                list.add(pos);
            }
        }

        return list;
    }

    public static void main(String[] args) {
        Problem2100 p = new Problem2100();

        System.out.println(LeetcodeUtil.equalsOfList(Collections.emptyList(),
                p.goodDaysToRobBank(new int[]{4, 3, 2, 1}, 1)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(2, 3),
                p.goodDaysToRobBank(new int[]{5, 3, 3, 3, 5, 6, 2}, 2)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(2, 6),
                p.goodDaysToRobBank(new int[]{5, 3, 3, 3, 5, 4, 2, 3, 4}, 2)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(0, 1, 2, 3, 4),
                p.goodDaysToRobBank(new int[]{1, 1, 1, 1, 1}, 0)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(0, 1, 2, 3),
                p.goodDaysToRobBank(new int[]{4, 3, 2, 1}, 0)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(2, 5),
                p.goodDaysToRobBank(new int[]{1, 1, 1, 2, 2, 0, 1, 1}, 2)));

        System.out.println(LeetcodeUtil.equalsOfList(Collections.emptyList(),
                p.goodDaysToRobBank(new int[]{1, 2, 3, 4, 5, 6}, 2)));

        System.out.println(LeetcodeUtil.equalsOfList(Collections.emptyList(),
                p.goodDaysToRobBank(new int[]{1}, 5)));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(2, 6, 25),
                p.goodDaysToRobBank(new int[]{5,3,3,3,5,4,2,3,4,6,7,3,4,5,3,4,5,5,6,7,6,5,6,4,3,2,4,5,6}, 2)));
    }
}
