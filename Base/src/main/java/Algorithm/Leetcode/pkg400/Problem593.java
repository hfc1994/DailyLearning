package Algorithm.Leetcode.pkg400;

/**
 * Created by hfc on 2022/3/7.
 *
 * 593. 有效的正方形
 *
 * 给定2D空间中四个点的坐标 p1, p2, p3 和 p4，如果这四个点构成一个正方形，则返回 true 。
 * 点的坐标 pi 表示为 [xi, yi] 。输入 不是 按任何顺序给出的。
 * 一个 有效的正方形 有四条等边和四个等角(90度角)。
 *
 * 示例 1:
 * 输入: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
 * 输出: True
 *
 * 示例 2:
 * 输入：p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,12]
 * 输出：false
 *
 * 示例 3:
 * 输入：p1 = [1,0], p2 = [-1,0], p3 = [0,1], p4 = [0,-1]
 * 输出：true
 *  
 * 提示:
 * p1.length == p2.length == p3.length == p4.length == 2
 * -10^4 <= xi, yi <= 10^4
 *
 */
public class Problem593 {

    /**
     * 速度 100%
     * 内存 45%
     */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        if (this.doCalc(p1, p2, p3) && this.doCalc(p4, p2, p3)) {
            return true;
        }

        if (this.doCalc(p1, p2, p4) && this.doCalc(p3, p2, p4)) {
            return true;
        }

        if (this.doCalc(p1, p3, p4) && this.doCalc(p2, p3, p4)) {
            return true;
        }

        return false;
    }

    private boolean doCalc(int[] p1, int[] p2, int[] p3) {
        // 不是同一个点
        if (p2[0] == p3[0] && p2[1] == p3[1]) {
            return false;
        }

        int[] v1 = new int[] {(p3[0] - p1[0]), (p3[1] - p1[1])};
        int[] v2 = new int[] {(p2[0] - p1[0]), (p2[1] - p1[1])};

        // 向量和
        int vector = v1[0] * v2[0] + v1[1] * v2[1];
        // 是直角
        if (vector == 0) {
            // 边长相等
            return v1[0] * v1[0] + v1[1] * v1[1] == v2[0] * v2[0] + v2[1] * v2[1];
        }
        return false;
    }

    public static void main(String[] args) {
        Problem593 p = new Problem593();

        System.out.println(p.validSquare(new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 0}, new int[]{0, 1}));
        System.out.println(!p.validSquare(new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 0}, new int[]{0, 12}));
        System.out.println(p.validSquare(new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1}));
        System.out.println(!p.validSquare(new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0}));
        System.out.println(!p.validSquare(new int[]{0, 0}, new int[]{0, 0}, new int[]{0, 0}, new int[]{1, 0}));
        System.out.println(!p.validSquare(new int[]{0, 0}, new int[]{0, 0}, new int[]{2, 0}, new int[]{1, 0}));
        System.out.println(!p.validSquare(new int[]{0, 0}, new int[]{5, 0}, new int[]{5, 4}, new int[]{0, 4}));
    }
}
