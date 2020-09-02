package Algorithm.Leetcode;

import java.util.*;

/**
 * Created by hfc on 2020/8/28.
 *
 * 332. 重新安排行程
 *
 * 给定一个机票的字符串二维数组 [from, to]，子数组中的两个成员分别表示飞机出发和降落的机场地点，
 * 对该行程进行重新规划排序。所有这些机票都属于一个从 JFK（肯尼迪国际机场）出发的先生，
 * 所以该行程必须从 JFK 开始。
 *
 * 说明:
 * 1、如果存在多种有效的行程，你可以按字符自然排序返回最小的行程组合。例如，行程 ["JFK", "LGA"] 与
 *    ["JFK", "LGB"] 相比就更小，排序更靠前
 * 2、所有的机场都用三个大写字母表示（机场代码）。
 * 3、假定所有机票至少存在一种合理的行程。
 *
 * 示例 1:
 * 输入: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
 * 输出: ["JFK", "MUC", "LHR", "SFO", "SJC"]
 *
 * 示例 2:
 * 输入: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
 * 输出: ["JFK","ATL","JFK","SFO","ATL","SFO"]
 * 解释: 另一种有效的行程是 ["JFK","SFO","ATL","JFK","ATL","SFO"]。但是它自然排序更大更靠后。
 *
 */
public class Problem332 {

    private int pointCount;     // 总行程涉及的点数，即比边数多1
    private Map<String, Direct> table;  // 类似邻接表

    public List<String> findItinerary(List<List<String>> tickets) {
        if (tickets.size() == 0)
            return Collections.emptyList();

        table = new HashMap<>();
        pointCount = tickets.size() + 1;
        tickets.forEach(tkt -> {
            if (table.get(tkt.get(0)) == null) {
                table.put(tkt.get(0), new Direct(tkt.get(1)));
            } else {
                Direct d = table.get(tkt.get(0));
                String strTo = tkt.get(1);

                Direct prev = null;
                // 地点字符串均为3个字母，hashcode值还在整数范围内
                while ( d != null && d.to.hashCode() < strTo.hashCode()) {
                    prev = d;
                    d = d.next;
                }

                // 对邻接表里面的Direct做好提前排序，字典顺序
                Direct newDirect = new Direct(strTo);
                if (d == null) {
                    prev.next = newDirect;
                } else if (prev == null) {  // 头节点
                    table.put(tkt.get(0), newDirect);
                    newDirect.next = d;
                } else {    // 其余节点
                    prev.next = newDirect;
                    newDirect.next = d;
                }
            }
        });

        List<String> points = new ArrayList<>(pointCount);
        findPath("JFK", 0, points);

        return points;
    }

    // 按题意，实际上只需要一条最短的（字母顺序），那么在实际构建时
    // 如果一个点有多个邻接点，那么按从小到大访问，完成一条构建就结束
    private void findPath(String from, int count, List<String> points) {
        points.add(from);
        if (points.size() == pointCount) {
            return;
        }

        // 因为提前排序，所以这里的Direct都是从小到大取出的
        Direct d = table.get(from);
        while (d != null) {
            if (!d.used) {
                d.used = true;
                findPath(d.to, count+1, points);

                // 本题只要最佳的组合，又因为Direct是从小到大取出的
                // 所以一旦有符合条件的组和，那么就是最佳的组合
                if (points.size() == pointCount)
                    return;
                else
                    points.remove(points.size() - 1);
                d.used = false;
            }
            d = d.next;
        }
    }

    /**
     * 找出一组字符串中的最佳字符串
     * 即按照字典顺序的最小字符串
     */
//    private String findBestString(List<String> paths) {
//        // w1和w2是在对应字符串里的字符索引，前4个字符是固定的（JFK,）
//        int wIndex1 = 4, wIndex2 = 4;
//        // t和s是在paths对应字符串的索引，t表示目标索引
//        int tIndex = 0, sIndex = 1;
//        boolean hit = false;
//        String str1 = paths.get(tIndex);
//        String str2 = paths.get(sIndex);
//        int ret;
//        while (!hit) {
//            ret = str1.charAt(wIndex1) - str2.charAt(wIndex2);
//            if (ret == 0) {
//                // 前面过滤过，所以不存在完全相同的行程组合，即从头到尾ret都等于0
//                wIndex1++;
//                wIndex2++;
//            } else if (ret < 0) {
//                // 比较到paths末尾了
//                if (sIndex == paths.size() - 1) {
//                    hit = true;
//                    continue;
//                }
//                str2 = paths.get(++sIndex);
//                wIndex1 = wIndex2 = 4;
//            } else {
//                str1 = str2;
//                if (sIndex == paths.size() - 1) {
//                    hit = true;
//                    continue;
//                }
//                str2 = paths.get(++sIndex);
//                wIndex1 = wIndex2 = 4;
//            }
//        }
//        return str1;
//    }
//
//    private int doCompare(String str1, String str2) {
//        int ret;
//        // 当前题目里sr1和str2是等长的
//        for (int i=0; i<str1.length(); i++) {
//            ret = str1.charAt(i) - str2.charAt(i);
//            if (ret != 0)
//                return ret;
//        }
//
//        // 当前题目不会走到这里
//        return 0;
//    }

    class Direct {
        String to;   // 指向的点
        boolean used;   // 该箭头是否已经被用
        Direct next;

        public Direct(String to) {
            this.to = to;
            used = false;
            next = null;
        }
    }

    public static void main(String[] args) {
        Problem332 p = new Problem332();

        List<List<String>> tickets = buildDataSet0();
//        List<List<String>> tickets = buildDataSet1();
//        List<List<String>> tickets = buildDataSet2();
//        List<List<String>> tickets = buildDataSet3();
//        List<List<String>> tickets = buildDataSet4();

        List<String> ret = p.findItinerary(tickets);
        ret.forEach(r -> System.out.print(r + ","));
        System.out.println();
    }

    private static List<List<String>> buildDataSet0() {
        return new ArrayList<>();
    }

    private static List<List<String>> buildDataSet1() {
        List<List<String>> tickets = new ArrayList<>();

        tickets.add(Arrays.asList("MUC", "LHR"));
        tickets.add(Arrays.asList("JFK", "MUC"));
        tickets.add(Arrays.asList("SFO", "SJC"));
        tickets.add(Arrays.asList("LHR", "SFO"));

        return tickets;
    }

    private static List<List<String>> buildDataSet2() {
        List<List<String>> tickets = new ArrayList<>();

        tickets.add(Arrays.asList("JFK", "SFO"));
        tickets.add(Arrays.asList("JFK", "ATL"));
        tickets.add(Arrays.asList("SFO", "ATL"));
        tickets.add(Arrays.asList("ATL", "JFK"));
        tickets.add(Arrays.asList("ATL", "SFO"));

        return tickets;
    }

    // leetcode提交时测试用例
    private static List<List<String>> buildDataSet3() {
        List<List<String>> tickets = new ArrayList<>();

        tickets.add(Arrays.asList("EZE", "AXA"));
        tickets.add(Arrays.asList("TIA", "ANU"));
        tickets.add(Arrays.asList("ANU", "JFK"));
        tickets.add(Arrays.asList("JFK", "ANU"));
        tickets.add(Arrays.asList("ANU", "EZE"));
        tickets.add(Arrays.asList("TIA", "ANU"));
        tickets.add(Arrays.asList("AXA", "TIA"));
        tickets.add(Arrays.asList("TIA", "JFK"));
        tickets.add(Arrays.asList("ANU", "TIA"));
        tickets.add(Arrays.asList("JFK", "TIA"));

        return tickets;
    }

    // leetcode提交时测试用例
    private static List<List<String>> buildDataSet4() {
        List<List<String>> tickets = new ArrayList<>();

        tickets.add(Arrays.asList("AXA","EZE"));
        tickets.add(Arrays.asList("EZE","AUA"));
        tickets.add(Arrays.asList("ADL","JFK"));
        tickets.add(Arrays.asList("ADL","TIA"));
        tickets.add(Arrays.asList("AUA","AXA"));
        tickets.add(Arrays.asList("EZE","TIA"));
        tickets.add(Arrays.asList("EZE","TIA"));
        tickets.add(Arrays.asList("AXA","EZE"));
        tickets.add(Arrays.asList("EZE","ADL"));
        tickets.add(Arrays.asList("ANU","EZE"));
        tickets.add(Arrays.asList("TIA","EZE"));
        tickets.add(Arrays.asList("JFK","ADL"));
        tickets.add(Arrays.asList("AUA","JFK"));
        tickets.add(Arrays.asList("JFK","EZE"));
        tickets.add(Arrays.asList("EZE","ANU"));
        tickets.add(Arrays.asList("ADL","AUA"));
        tickets.add(Arrays.asList("ANU","AXA"));
        tickets.add(Arrays.asList("AXA","ADL"));
        tickets.add(Arrays.asList("AUA","JFK"));
        tickets.add(Arrays.asList("EZE","ADL"));
        tickets.add(Arrays.asList("ANU","TIA"));
        tickets.add(Arrays.asList("AUA","JFK"));
        tickets.add(Arrays.asList("TIA","JFK"));
        tickets.add(Arrays.asList("EZE","AUA"));
        tickets.add(Arrays.asList("AXA","EZE"));
        tickets.add(Arrays.asList("AUA","ANU"));
        tickets.add(Arrays.asList("ADL","AXA"));
        tickets.add(Arrays.asList("EZE","ADL"));
        tickets.add(Arrays.asList("AUA","ANU"));
        tickets.add(Arrays.asList("AXA","EZE"));
        tickets.add(Arrays.asList("TIA","AUA"));
        tickets.add(Arrays.asList("AXA","EZE"));
        tickets.add(Arrays.asList("AUA","SYD"));
        tickets.add(Arrays.asList("ADL","JFK"));
        tickets.add(Arrays.asList("EZE","AUA"));
        tickets.add(Arrays.asList("ADL","ANU"));
        tickets.add(Arrays.asList("AUA","TIA"));
        tickets.add(Arrays.asList("ADL","EZE"));
        tickets.add(Arrays.asList("TIA","JFK"));
        tickets.add(Arrays.asList("AXA","ANU"));
        tickets.add(Arrays.asList("JFK","AXA"));
        tickets.add(Arrays.asList("JFK","ADL"));
        tickets.add(Arrays.asList("ADL","EZE"));
        tickets.add(Arrays.asList("AXA","TIA"));
        tickets.add(Arrays.asList("JFK","AUA"));
        tickets.add(Arrays.asList("ADL","EZE"));
        tickets.add(Arrays.asList("JFK","ADL"));
        tickets.add(Arrays.asList("ADL","AXA"));
        tickets.add(Arrays.asList("TIA","AUA"));
        tickets.add(Arrays.asList("AXA","JFK"));
        tickets.add(Arrays.asList("ADL","AUA"));
        tickets.add(Arrays.asList("TIA","JFK"));
        tickets.add(Arrays.asList("JFK","ADL"));
        tickets.add(Arrays.asList("JFK","ADL"));
        tickets.add(Arrays.asList("ANU","AXA"));
        tickets.add(Arrays.asList("TIA","AXA"));
        tickets.add(Arrays.asList("EZE","JFK"));
        tickets.add(Arrays.asList("EZE","AXA"));
        tickets.add(Arrays.asList("ADL","TIA"));
        tickets.add(Arrays.asList("JFK","AUA"));
        tickets.add(Arrays.asList("TIA","EZE"));
        tickets.add(Arrays.asList("EZE","ADL"));
        tickets.add(Arrays.asList("JFK","ANU"));
        tickets.add(Arrays.asList("TIA","AUA"));
        tickets.add(Arrays.asList("EZE","ADL"));
        tickets.add(Arrays.asList("ADL","JFK"));
        tickets.add(Arrays.asList("ANU","AXA"));
        tickets.add(Arrays.asList("AUA","AXA"));
        tickets.add(Arrays.asList("ANU","EZE"));
        tickets.add(Arrays.asList("ADL","AXA"));
        tickets.add(Arrays.asList("ANU","AXA"));
        tickets.add(Arrays.asList("TIA","ADL"));
        tickets.add(Arrays.asList("JFK","ADL"));
        tickets.add(Arrays.asList("JFK","TIA"));
        tickets.add(Arrays.asList("AUA","ADL"));
        tickets.add(Arrays.asList("AUA","TIA"));
        tickets.add(Arrays.asList("TIA","JFK"));
        tickets.add(Arrays.asList("EZE","JFK"));
        tickets.add(Arrays.asList("AUA","ADL"));
        tickets.add(Arrays.asList("ADL","AUA"));
        tickets.add(Arrays.asList("EZE","ANU"));
        tickets.add(Arrays.asList("ADL","ANU"));
        tickets.add(Arrays.asList("AUA","AXA"));
        tickets.add(Arrays.asList("AXA","TIA"));
        tickets.add(Arrays.asList("AXA","TIA"));
        tickets.add(Arrays.asList("ADL","AXA"));
        tickets.add(Arrays.asList("EZE","AXA"));
        tickets.add(Arrays.asList("AXA","JFK"));
        tickets.add(Arrays.asList("JFK","AUA"));
        tickets.add(Arrays.asList("ANU","ADL"));
        tickets.add(Arrays.asList("AXA","TIA"));
        tickets.add(Arrays.asList("ANU","AUA"));
        tickets.add(Arrays.asList("JFK","EZE"));
        tickets.add(Arrays.asList("AXA","ADL"));
        tickets.add(Arrays.asList("TIA","EZE"));
        tickets.add(Arrays.asList("JFK","AXA"));
        tickets.add(Arrays.asList("AXA","ADL"));
        tickets.add(Arrays.asList("EZE","AUA"));
        tickets.add(Arrays.asList("AXA","ANU"));
        tickets.add(Arrays.asList("ADL","EZE"));
        tickets.add(Arrays.asList("AUA","EZE"));

        return tickets;
    }
}
