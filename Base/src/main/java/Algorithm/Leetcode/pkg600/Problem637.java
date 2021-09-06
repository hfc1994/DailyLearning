package Algorithm.Leetcode.pkg600;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hfc on 2020/9/12.
 *
 * 637. 二叉树的层平均值
 *
 * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。
 *
 * 示例 1：
 * 输入：
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 输出：[3, 14.5, 11]
 *
 * 解释：
 * 第 0 层的平均值是 3 ,  第1层是 14.5 , 第2层是 11 。因此返回 [3, 14.5, 11] 。
 *
 * 提示：
 * 节点值的范围在32位有符号整数范围内。
 *
 */
public class Problem637 {

    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> rets = new LinkedList<>();

        if (root == null)
            return rets;

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);
        int left = 1, count = 1;
        double total = 0;
        TreeNode cur;
        while (true) {
            if (left != 0) {
                cur = queue.removeFirst();
                left--;

                total += cur.val;
                if (cur.left != null) queue.addLast(cur.left);
                if (cur.right != null) queue.addLast(cur.right);

            } else {
                rets.add((total * 1.0) / count);

                if (queue.isEmpty())
                    break;

                left = queue.size();
                count = left;
                total = 0;
            }
        }

        return rets;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) { val = x; }
    }

    public static void main(String[] args) {
        Problem637 p = new Problem637();

        TreeNode root = p.new TreeNode(3);
        TreeNode cur = root;
        TreeNode left = p.new TreeNode(9);
        TreeNode right = p.new TreeNode(20);
        cur.left = left;
        cur.right = right;
        cur = cur.right;
        left = p.new TreeNode(15);
        right = p.new TreeNode(7);
        cur.left = left;
        cur.right = right;

        System.out.print("[");
        p.averageOfLevels(root).forEach(ret -> System.out.print(ret + ","));
        System.out.println("]");

        root = p.new TreeNode(3);

        System.out.print("[");
        p.averageOfLevels(root).forEach(ret -> System.out.print(ret + ","));
        System.out.println("]");

        root = null;

        System.out.print("[");
        p.averageOfLevels(root).forEach(ret -> System.out.print(ret + ","));
        System.out.println("]");
    }

}
