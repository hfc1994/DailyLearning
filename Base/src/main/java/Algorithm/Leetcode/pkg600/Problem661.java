package Algorithm.Leetcode.pkg600;

import Algorithm.Leetcode.LeetcodeUtil;

/**
 * Created by hfc on 2022/3/24.
 *
 * 661. 图片平滑器
 *
 * 图像平滑器 是大小为 3 x 3 的过滤器，用于对图像的每个单元格平滑处理，平滑处理后单元
 * 格的值为该单元格的平均灰度。
 * 每个单元格的  平均灰度 定义为：该单元格自身及其周围的 8 个单元格的平均值，结果需向
 * 下取整。（即，需要计算蓝色平滑器中 9 个单元格的平均值）。
 * 如果一个单元格周围存在单元格缺失的情况，则计算平均灰度时不考虑缺失的单元格（即，需
 * 要计算红色平滑器中 4 个单元格的平均值）。
 *      蓝  蓝  蓝  白  白
 *      蓝  蓝  蓝  白  白
 *      蓝  蓝  蓝  白  白
 *      白  白  白  红  红
 *      白  白  白  红  红
 * 给你一个表示图像灰度的 m x n 整数矩阵 img ，返回对图像的每个单元格平滑处理后的图像 。
 *
 * 示例 1:
 *   1  1  1      0  0  0
 *   1  0  1  ->  0  0  0
 *   1  1  1      0  0  0
 * 输入:img = [[1,1,1],[1,0,1],[1,1,1]]
 * 输出:[[0, 0, 0],[0, 0, 0], [0, 0, 0]]
 * 解释:
 * 对于点 (0,0), (0,2), (2,0), (2,2): 平均(3/4) = 平均(0.75) = 0
 * 对于点 (0,1), (1,0), (1,2), (2,1): 平均(5/6) = 平均(0.83333333) = 0
 * 对于点 (1,1): 平均(8/9) = 平均(0.88888889) = 0
 *
 * 示例 2:
 *   100  200  100      137  141  137
 *   200  50   200  ->  141  138  141
 *   100  200  100      137  141  137
 * 输入: img = [[100,200,100],[200,50,200],[100,200,100]]
 * 输出: [[137,141,137],[141,138,141],[137,141,137]]
 * 解释:
 * 对于点 (0,0), (0,2), (2,0), (2,2): floor((100+200+200+50)/4) = floor(137.5) = 137
 * 对于点 (0,1), (1,0), (1,2), (2,1): floor((200+200+50+200+100+100)/6) = floor(141.666667) = 141
 * 对于点 (1,1): floor((50+200+200+200+200+100+100+100+100)/9) = floor(138.888889) = 138
 *
 * 提示:
 * m == img.length
 * n == img[i].length
 * 1 <= m, n <= 200
 * 0 <= img[i][j] <= 255
 *
 */
public class Problem661 {

    /**
     * 评论区给的方法
     * 速度 90%
     * 内存 14%
     */
    public int[][] imageSmoother(int[][] img) {
        int[][] newImg = new int[img.length][img[0].length];
        int[][] vectors = new int[][] {
                {-1, -1}, {0, -1}, {1, -1},
                {-1, 0},  {0, 0},  {1, 0},
                {-1, 1},  {0, 1},  {1, 1},
        };

        int xLen = img.length;
        int yLen = img[0].length;
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                int total = 0;
                int count = 0;
                for (int[] v : vectors) {
                    int x = i + v[0];
                    int y = j + v[1];

                    if (x >= 0 && x < xLen && y >= 0 && y < yLen) {
                        total += img[x][y];
                        count++;
                    }
                }

                newImg[i][j] = total / count;
            }
        }
        return newImg;
    }

    public static void main(String[] args) {
        Problem661 p = new Problem661();

        LeetcodeUtil.print2DArray(p.imageSmoother(new int[][] {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}}));
        LeetcodeUtil.print2DArray(p.imageSmoother(new int[][] {{100, 200, 100}, {200, 50, 200}, {100, 200, 100}}));
    }

}
