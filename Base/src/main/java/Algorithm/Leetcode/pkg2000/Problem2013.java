package Algorithm.Leetcode.pkg2000;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hfc on 2022/3/7.
 *
 * 2013. 检测正方形
 *
 * 给你一个在 X-Y 平面上的点构成的数据流。设计一个满足下述要求的算法：
 * - 添加 一个在数据流中的新点到某个数据结构中。可以添加 重复 的点，并会视作不同的点进行处理。
 * - 给你一个查询点，请你从数据结构中选出三个点，使这三个点和查询点一同构成一个 面积为正 的
 *   轴对齐正方形 ，统计 满足该要求的方案数目。轴对齐正方形 是一个正方形，除四条边长度相同外，
 *   还满足每条边都与 x-轴 或 y-轴 平行或垂直。
 *
 * 实现 DetectSquares 类：
 * - DetectSquares() 使用空数据结构初始化对象
 * - void add(int[] point) 向数据结构添加一个新的点 point = [x, y]
 * - int count(int[] point) 统计按上述方式与点 point = [x, y] 共同构造 轴对齐正方形 的方案数。
 *
 * 示例：
 * 输入：
 * ["DetectSquares", "add", "add", "add", "count", "count", "add", "count"]
 * [[], [[3, 10]], [[11, 2]], [[3, 2]], [[11, 10]], [[14, 8]], [[11, 2]], [[11, 10]]]
 * 输出：
 * [null, null, null, null, 1, 0, null, 2]
 *
 * 解释：
 * DetectSquares detectSquares = new DetectSquares();
 * detectSquares.add([3, 10]);
 * detectSquares.add([11, 2]);
 * detectSquares.add([3, 2]);
 * detectSquares.count([11, 10]); // 返回 1 。你可以选择：
 *                                //   - 第一个，第二个，和第三个点
 * detectSquares.count([14, 8]);  // 返回 0 。查询点无法与数据结构中的这些点构成正方形。
 * detectSquares.add([11, 2]);    // 允许添加重复的点。
 * detectSquares.count([11, 10]); // 返回 2 。你可以选择：
 *                                //   - 第一个，第二个，和第三个点
 *                                //   - 第一个，第三个，和第四个点
 *
 * 提示：
 * point.length == 2
 * 0 <= x, y <= 1000
 * 调用 add 和 count 的 总次数 最多为 5000
 *
 */
public class Problem2013 {

    /**
     * 速度 69%
     * 内存 54%
     */
    class DetectSquares {

        Map<Integer, List<PointInfo>> xMap;
        Map<Integer, List<PointInfo>> yMap;

        public DetectSquares() {
            xMap = new HashMap<>();
            yMap = new HashMap<>();
        }

        public void add(int[] point) {
            List<PointInfo> pList = xMap.computeIfAbsent(point[0], key -> new ArrayList<>());
            boolean exist = false;
            for (PointInfo pi : pList) {
                if (pi.coord[0] == point[0] && pi.coord[1] == point[1]) {
                    pi.cnt++;
                    exist = true;
                }
            }

            if (!exist) {
                PointInfo pi = new PointInfo(point);
                pList.add(pi);
                pList = yMap.computeIfAbsent(point[1], key -> new ArrayList<>());
                pList.add(pi);
            }
        }

        public int count(int[] point) {
            int x = point[0];
            int y = point[1];
            // x/y 轴上都得有至少一个相同 x/y 值的点才行
            if (!xMap.containsKey(x)) return 0;
            if (!yMap.containsKey(y)) return 0;

            int count  = 0;
            List<PointInfo> pList = xMap.get(x);
            for (PointInfo pi : pList) {
                // 需要不相同的点
                if (pi.coord[1] != y) {
                    count += this.calcSquares(pi, point);
                }
            }

            return count;
        }

        private int calcSquares(PointInfo pi, int[] point) {
            int edgeLen = Math.abs(pi.coord[1] - point[1]);
            return this.findSquares(pi, point, xMap.get(point[0] - edgeLen))
                    + this.findSquares(pi, point, xMap.get(point[0] + edgeLen));
        }

        private int findSquares(PointInfo pi, int[] point, List<PointInfo> pList) {
            if (pList == null) return 0;

            PointInfo a = null, b = null;
            for (PointInfo tmp : pList) {
                if (tmp.coord[1] == pi.coord[1]) {
                    a = tmp;
                } else if (tmp.coord[1] == point[1]) {
                    b = tmp;
                }
            }

            if (a == null || b == null) {
                return 0;
            }
            return pi.cnt * a.cnt * b.cnt;
        }
    }

    class PointInfo {
        int cnt;
        int[] coord;

        public PointInfo(int[] point) {
            this.coord = point;
            this.cnt = 1;
        }
    }

    public static void main(String[] args) {
        Problem2013 p = new Problem2013();

        DetectSquares detect = p.new DetectSquares();
        detect.add(new int[] {3, 10});
        detect.add(new int[] {11, 2});
        detect.add(new int[] {3, 2});
        System.out.println(1 == detect.count(new int[] {11, 10}));
        System.out.println(0 == detect.count(new int[] {11, 9}));
        System.out.println(0 == detect.count(new int[] {11, 2}));
        System.out.println(0 == detect.count(new int[] {14, 8}));
        detect.add(new int[] {11, 2});
        System.out.println(2 == detect.count(new int[] {11, 10}));
        detect.add(new int[] {3, 10});
        System.out.println(4 == detect.count(new int[] {11, 10}));

    }

}
