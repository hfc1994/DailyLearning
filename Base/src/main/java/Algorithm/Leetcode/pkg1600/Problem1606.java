package Algorithm.Leetcode.pkg1600;

import Algorithm.Leetcode.LeetcodeUtil;

import java.util.*;

/**
 * Created by hfc on 2022/3/30.
 *
 * 1606. 找到处理最多请求的服务器
 *
 * 你有 k 个服务器，编号为 0 到 k-1 ，它们可以同时处理多个请求组。每个服务器有无穷的计算能力但是
 * 不能同时处理超过一个请求 。请求分配到服务器的规则如下：
 * - 第 i （序号从 0 开始）个请求到达。
 * - 如果所有服务器都已被占据，那么该请求被舍弃（完全不处理）。
 * - 如果第 (i % k) 个服务器空闲，那么对应服务器会处理该请求。
 * - 否则，将请求安排给下一个空闲的服务器（服务器构成一个环，必要的话可能从第 0 个服务器开始继续找
 *   下一个空闲的服务器）。比方说，如果第 i 个服务器在忙，那么会查看第 (i+1) 个服务器，第 (i+2) 个
 *   服务器等等。
 * 给你一个 严格递增 的正整数数组 arrival ，表示第 i 个任务的到达时间，和另一个数组 load ，其中 load[i] 
 * 表示第 i 个请求的工作量（也就是服务器完成它所需要的时间）。你的任务是找到 最繁忙的服务器 。最繁忙定义
 * 为一个服务器处理的请求数是所有服务器里最多的。
 * 请你返回包含所有 最繁忙服务器 序号的列表，你可以以任意顺序返回这个列表。
 *
 * 示例 1：
 * 输入：k = 3, arrival = [1,2,3,4,5], load = [5,2,3,3,3]
 * 输出：[1]
 * 解释：
 * 所有服务器一开始都是空闲的。
 * 前 3 个请求分别由前 3 台服务器依次处理。
 * 请求 3 进来的时候，服务器 0 被占据，所以它呗安排到下一台空闲的服务器，也就是服务器 1 。
 * 请求 4 进来的时候，由于所有服务器都被占据，该请求被舍弃。
 * 服务器 0 和 2 分别都处理了一个请求，服务器 1 处理了两个请求。所以服务器 1 是最忙的服务器。
 *
 * 示例 2：
 * 输入：k = 3, arrival = [1,2,3,4], load = [1,2,1,2]
 * 输出：[0]
 * 解释：
 * 前 3 个请求分别被前 3 个服务器处理。
 * 请求 3 进来，由于服务器 0 空闲，它被服务器 0 处理。
 * 服务器 0 处理了两个请求，服务器 1 和 2 分别处理了一个请求。所以服务器 0 是最忙的服务器。
 *
 * 示例 3：
 * 输入：k = 3, arrival = [1,2,3], load = [10,12,11]
 * 输出：[0,1,2]
 * 解释：每个服务器分别处理了一个请求，所以它们都是最忙的服务器。
 *
 * 示例 4：
 * 输入：k = 3, arrival = [1,2,3,4,8,9,10], load = [5,2,10,3,1,2,2]
 * 输出：[1]
 *
 * 示例 5：
 * 输入：k = 1, arrival = [1], load = [1]
 * 输出：[0]
 *
 * 提示：
 * 1 <= k <= 10^5
 * 1 <= arrival.length, load.length <= 10^5
 * arrival.length == load.length
 * 1 <= arrival[i], load[i] <= 10^9
 * arrival 保证 严格递增 。
 *
 */
public class Problem1606 {

    /**
     * 时间超限
     */
    public List<Integer> busiestServers1(int k, int[] arrival, int[] load) {
        int[] bizTime = new int[k];
        int[] bizCnt = new int[k];

        int maxCnt = 0;
        List<Integer> result =new LinkedList<>();
        for (int i = 0; i < arrival.length; i++) {
            int idx = i % k;
            // TODO: 2022/3/30 优化这个
            if (arrival[i] < bizTime[idx]) {
                int initIdx = idx;
                idx = (idx + 1) % k;
                while (initIdx != idx) {
                    if (arrival[i] >= bizTime[idx]) {
                        break;
                    }
                    idx = (idx + 1) % k;
                }

                if (initIdx == idx) {
                    continue;   // 丢弃当前
                }
            }

            bizCnt[idx]++;
            bizTime[idx] = arrival[i] + load[i];
            if (bizCnt[idx] > maxCnt) {
                maxCnt = bizCnt[idx];
                result.clear();
                result.add(idx);
            } else if (bizCnt[idx] == maxCnt) {
                result.add(idx);
            }
        }

        return result;
    }

    /**
     * 时间超限
     */
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeMap<Integer, Set<Integer>> treeMap = new TreeMap<>();
        int[] bizTime = new int[k];
        int[] bizCnt = new int[k];

        int maxCnt = 0;
        List<Integer> result =new LinkedList<>();
        for (int i = 0; i < arrival.length; i++) {
            int idx = i % k;
            if (arrival[i] < bizTime[idx]) {
                int miniDiff = Integer.MAX_VALUE;
                int targetIdx = -1;
                // FIXME: 2022/3/31 如果连续两次要偏移，提前出map会导致第二次找不到合适的
                // TODO: 2022/3/31 要不这里只做遍历，后面remove idx的地方再remove对应的key
                while (treeMap.firstKey() <= arrival[i]) {
                    Map.Entry<Integer, Set<Integer>> entry = treeMap.pollFirstEntry();
                    for (Integer tmpIdx : entry.getValue()) {
                        int diff = (tmpIdx > idx) ? tmpIdx - idx : k - idx + tmpIdx;
                        if (diff < miniDiff) {
                            miniDiff = diff;
                            targetIdx = tmpIdx;
                        }
                    }
                }
                if (targetIdx == -1) {
                    continue; // 丢弃当前
                }

                idx = targetIdx;
            }

            int tmpVal = bizTime[idx];
            if (tmpVal != 0 && treeMap.containsKey(tmpVal)) {
                treeMap.get(tmpVal).remove(idx);
            }

            tmpVal = arrival[i] + load[i];
            bizTime[idx] = tmpVal;
            treeMap.computeIfAbsent(tmpVal, key -> new HashSet<>()).add(idx);

            bizCnt[idx]++;
            if (bizCnt[idx] > maxCnt) {
                maxCnt = bizCnt[idx];
                result.clear();
                result.add(idx);
            } else if (bizCnt[idx] == maxCnt) {
                result.add(idx);
            }
        }

        return result;
    }

    public List<Integer> busiestServers3(int k, int[] arrival, int[] load) {
        TreeMap<Integer, Set<Integer>> treeMap = new TreeMap<>();
        int[] bizTime = new int[k];
        int[] bizCnt = new int[k];

        int maxCnt = 0;
        List<Integer> result =new LinkedList<>();
        for (int i = 0; i < arrival.length; i++) {
            int idx = i % k;
            if (arrival[i] < bizTime[idx]) {
                int miniDiff = Integer.MAX_VALUE;
                int targetIdx = -1;
                for (Map.Entry<Integer, Set<Integer>> entry : treeMap.entrySet()) {
                    if (entry.getKey() > arrival[i]) {
                        break;
                    }
                    for (Integer tmpIdx : entry.getValue()) {
                        int diff = (tmpIdx > idx) ? tmpIdx - idx : k - idx + tmpIdx;
                        if (diff < miniDiff) {
                            miniDiff = diff;
                            targetIdx = tmpIdx;
                        }
                    }
                }
                if (targetIdx == -1) {
                    continue; // 丢弃当前
                }

                idx = targetIdx;
                treeMap.remove(idx);
            }

            int tmpVal = bizTime[idx];
            if (tmpVal != 0) treeMap.get(tmpVal).remove(idx);

            tmpVal = arrival[i] + load[i];
            bizTime[idx] = tmpVal;
            treeMap.computeIfAbsent(tmpVal,
                    key -> new HashSet<>()).add(idx);

            bizCnt[idx]++;
            if (bizCnt[idx] > maxCnt) {
                maxCnt = bizCnt[idx];
                result.clear();
                result.add(idx);
            } else if (bizCnt[idx] == maxCnt) {
                result.add(idx);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Problem1606 p = new Problem1606();

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1),
                p.busiestServers(3, new int[] {3,4,6,8,9,11,12,16}, new int[] {1,2,8,6,5,3,8,3})));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1),
                p.busiestServers(3, new int[] {1, 2, 3, 4, 5}, new int[] {5, 2, 3, 3, 3})));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(0),
                p.busiestServers(3, new int[] {1, 2, 3, 4}, new int[] {1, 2, 1, 2})));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(0, 1, 2),
                p.busiestServers(3, new int[] {1, 2, 3}, new int[] {10, 12, 11})));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(1),
                p.busiestServers(3, new int[] {1, 2, 3, 4, 8, 9, 10}, new int[] {5, 2, 10, 3, 1, 2, 2})));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(0),
                p.busiestServers(1, new int[] {1}, new int[] {1})));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(0),
                p.busiestServers(7, new int[] {1,3,4,5,6,11,12,13,15,19,20,21,23,25,31,32}, new int[] {9,16,14,1,5,15,6,10,1,1,7,5,11,4,4,6})));

        System.out.println(LeetcodeUtil.equalsOfList(Arrays.asList(0, 1, 2, 3, 4),
                p.busiestServers(5, new int[] {1,2,3,4,5,7,8,12,13,14,17,18,20,23,25,26,28,29,30,31}, new int[] {5,2,3,3,3,4,3,2,5,5,5,4,3,1,2,1,3,5,3,4})));
    }

}
